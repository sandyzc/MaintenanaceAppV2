package com.example.maintenanceappv2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.algolia.search.saas.RequestOptions;
import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.adaptors.AlgoliaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Algolia_search extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlgoliaAdapter adapter;
    private List<SapMaterialSearchModel> itemList;
    private Index index;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algolia_search);

        recyclerView = findViewById(R.id.algolia_rcv);
        itemList = new ArrayList<>();
        adapter = new AlgoliaAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize Algolia index
        Algolia_app app = (Algolia_app) getApplicationContext();
        index = app.getIndex();
        executorService = Executors.newSingleThreadExecutor();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
           //     performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                performSearch(newText);
                return false;
            }
        });
    }



//    private void performSearch(String query) {
//        executorService.execute(() -> {
//            List<SapMaterialSearchModel> results = new ArrayList<>();
//            try {
//                Query algoliaQuery = new Query(query);
//                RequestOptions requestOptions = new RequestOptions();
//                JSONObject content = index.search(algoliaQuery, requestOptions);
//                JSONArray hits = content.getJSONArray("hits");
//            //    Toast.makeText(getApplicationContext(), hits.length() +"results found", Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < hits.length(); i++) {
//                    JSONObject hit = hits.getJSONObject(i);
//                    SapMaterialSearchModel item = new SapMaterialSearchModel();
//
//                  item.setDescription(hit.getString("description"));
//                    item.setCatergory(hit.getString("catergory"));
//                    item.setGen_desc(hit.getString("gen_desc"));
//                    item.setMachine(hit.getString("machine"));
//                   item.setLocation(hit.getString("location"));
//                    item.setSap_code(hit.getInt("sap_code"));
//                   item.setStock(hit.getInt("stock"));
//                    item.setStation(hit.getString("station"));
//                    item.setSub_assembly(hit.getString("sub_assembly"));
//                    item.setUsed_in_1(hit.getString("used_in_1"));
//                    item.setUsed_in_2(hit.getString("used_in_2"));
//                    item.setUsed_in_3(hit.getString("used_in_3"));
//                    item.setUsed_in_4(hit.getString("used_in_4"));
//                    item.setVendor(hit.getString("vendor"));
//                    item.setImage(hit.getString("image"));
//                    // Set other fields as needed
//                    results.add(item);
//                }
//            } catch (AlgoliaException | JSONException e) {
//                e.printStackTrace();
//            }
//            runOnUiThread(() -> adapter.updateList(results));
//
//        });
//    }


}
