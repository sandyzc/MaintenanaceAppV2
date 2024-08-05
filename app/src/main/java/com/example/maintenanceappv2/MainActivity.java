package com.example.maintenanceappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.adaptors.SapMaterialViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private SapMaterialViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SapMaterialViewModel.class);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Button  materialSearchbtn= findViewById(R.id.material_search_btn);




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


    // Fetch data from Firestore and save it to the local database
    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("web_app").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<SapMaterialSearchModel> materials = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            SapMaterialSearchModel material = document.toObject(SapMaterialSearchModel.class);
                            material.setId(document.getId());
                            materials.add(material);
                        }
                        viewModel.insertAll(materials);
                        runOnUiThread(this::navigateToAlgoliaSearch);
                        navigateToAlgoliaSearch();
                    } else {
                        // Handle the error
                    }
                });
    }


    // Navigate to the Material_results activity
    private void navigateToAlgoliaSearch() {
        Intent intent = new Intent(this, Material_results.class);
        startActivity(intent);
        finish();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search_options, null);
        builder.setView(dialogView);

        EditText keywordEditText = dialogView.findViewById(R.id.keywordEditText);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);

        builder.setTitle("Search Materials")
                .setPositiveButton("Search", (dialog, which) -> {
                    String keyword = keywordEditText.getText().toString().trim();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedId);
                    String searchColumn = selectedRadioButton.getText().toString().toLowerCase();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", keyword);
                    bundle.putString("searchColumn", searchColumn);
                    Intent intent = new Intent(MainActivity.this, Material_results.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }




}