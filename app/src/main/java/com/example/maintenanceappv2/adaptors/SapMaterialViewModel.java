package com.example.maintenanceappv2.adaptors;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;

import java.util.List;

public class SapMaterialViewModel extends AndroidViewModel {

    private final SapMaterialRepository repository;

    public SapMaterialViewModel(Application application) {
        super(application);
        repository = new SapMaterialRepository(application);
    }

    public void insertAll(List<SapMaterialSearchModel> materials) {
        repository.insertAll(materials);
    }

    public LiveData<List<SapMaterialSearchModel>> searchMaterials(String query) {
        return repository.searchMaterials(query);
    }

    public LiveData<List<SapMaterialSearchModel>> searchMaterials(String query, String column) {
        return repository.searchMaterials(query, column);
    }

    public LiveData<Integer> getLocalDatabaseCount() {
        return repository.getLocalDatabaseCount();
    }
}


