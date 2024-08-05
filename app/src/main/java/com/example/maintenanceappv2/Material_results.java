package com.example.maintenanceappv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.adaptors.AlgoliaAdapter;
import com.example.maintenanceappv2.adaptors.SapMaterialViewModel;

import java.util.ArrayList;
import java.util.List;

public class Material_results extends AppCompatActivity {
    private SapMaterialViewModel viewModel;
    private RecyclerView recyclerView;
    private AlgoliaAdapter adapter;
    String keyword;
    String searchColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_results);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             keyword = bundle.getString("keyword");
             searchColumn = bundle.getString("searchColumn");

        }

        recyclerView = findViewById(R.id.algolia_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlgoliaAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SapMaterialViewModel.class);
        performSearch(keyword, searchColumn);
        


    }

    private void performSearch(String keyword, String searchColumn) {
        viewModel.searchMaterials(keyword, searchColumn).observe(this, new Observer<List<SapMaterialSearchModel>>() {
            @Override
            public void onChanged(List<SapMaterialSearchModel> sapMaterialSearchModels) {
                adapter.updateList(sapMaterialSearchModels);
            }
        });
    }


    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.searchEditText);
        Button searchButton = dialogView.findViewById(R.id.searchButton);

        AlertDialog dialog = builder.create();
        searchButton.setOnClickListener(view -> {
            String query = searchEditText.getText().toString();
            viewModel.searchMaterials(query).observe(this, results -> adapter.updateList(results));
            dialog.dismiss();
        });

        dialog.show();
    }
}

