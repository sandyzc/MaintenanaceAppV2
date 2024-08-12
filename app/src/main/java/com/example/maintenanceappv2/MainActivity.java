package com.example.maintenanceappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.adaptors.SapMaterialViewModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SapMaterialViewModel viewModel;
    private LottieAnimationView  dotLottieAnimationView;
    private View blurOverlay;
    private View blurView;
    private ViewGroup blurContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dotLottieAnimationView = findViewById(R.id.lottieAnimationView);
        blurOverlay = findViewById(R.id.blurOverlay);
        blurView = findViewById(R.id.blurView);
        blurContainer = findViewById(R.id.main_activi);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SapMaterialViewModel.class);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Button materialSearchbtn = findViewById(R.id.material_search_btn);


        materialSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.getLocalDatabaseCount().observe(MainActivity.this, count -> {
                    if (count != null && count == 0) {

                        fetchDataFromFirestore();
                    } else {
                        showSearchDialog();
                    }
                });
            }
        });
    }


    private void fetchDataFromFirestore() {
        // Your existing fetchDataFromFirestore code

        // Show Lottie animation
        blurOverlay.setVisibility(View.VISIBLE);
        dotLottieAnimationView.setVisibility(View.VISIBLE);
        dotLottieAnimationView.playAnimation();
        Blurry.with(MainActivity.this).onto(blurContainer);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("web_app").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<SapMaterialSearchModel> materials = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    SapMaterialSearchModel material = document.toObject(SapMaterialSearchModel.class);
                    material.setId(document.getId());
                    materials.add(material);
                }
                viewModel.insertAll(materials);
                Toast.makeText(MainActivity.this, "Data synced from Firestore", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
            }

            dotLottieAnimationView.cancelAnimation();
            dotLottieAnimationView.setVisibility(View.GONE);
            blurOverlay.setVisibility(View.GONE);
            Blurry.delete(blurContainer);
        });
    }


    private void addFirestoreListener() {
        db.collection("web_app").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, "Listen failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && !value.isEmpty() && !value.getMetadata().hasPendingWrites()) {
                    // Check if there are document changes
                    Toast.makeText(MainActivity.this, "document changes available so updating", Toast.LENGTH_SHORT).show();
                    if (!value.getDocumentChanges().isEmpty()) {
                        fetchDataFromFirestore();
                    }
                }
            }
        });
    }


    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search_options, null);
        builder.setView(dialogView);

        EditText keywordEditText = dialogView.findViewById(R.id.keywordEditText);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);

        builder.setTitle("Search Materials").setPositiveButton("Search", (dialog, which) -> {
            String keyword = keywordEditText.getText().toString().trim();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = dialogView.findViewById(selectedId);

            String searchText = keywordEditText.getText().toString().trim();

            if (searchText.isEmpty()) {
                keywordEditText.setError("Search field can't be empty");
                keywordEditText.requestFocus();
                return;
            }

            int selectedOptionId = radioGroup.getCheckedRadioButtonId();
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Please select a search option", Toast.LENGTH_SHORT).show();
                return;
            }

            String searchColumn = selectedRadioButton.getText().toString().toLowerCase();
            Bundle bundle = new Bundle();
            bundle.putString("keyword", keyword);
            bundle.putString("searchColumn", searchColumn);
            Intent intent = new Intent(MainActivity.this, Material_results.class);
            intent.putExtras(bundle);
            startActivity(intent);


        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).create().show();
    }


}