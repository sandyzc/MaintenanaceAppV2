package com.example.maintenanceappv2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.adaptors.Material_from_firestore_adaptor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Material_results extends AppCompatActivity {

    CollectionReference reference = FirebaseFirestore.getInstance().collection("web_app");
    RecyclerView material_results;
    Material_from_firestore_adaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_material_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });

        material_results=findViewById(R.id.material_search_list_rcv);


        Query query = reference.orderBy("sap_code", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<SapMaterialSearchModel> options = new FirestoreRecyclerOptions.Builder<SapMaterialSearchModel>().setQuery(query, SapMaterialSearchModel.class).build();

         adaptor = new Material_from_firestore_adaptor(options,this);

        material_results.setHasFixedSize(true);
        material_results.setLayoutManager(new LinearLayoutManager(this));
        material_results.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        material_results.setAdapter(adaptor);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
    }
}