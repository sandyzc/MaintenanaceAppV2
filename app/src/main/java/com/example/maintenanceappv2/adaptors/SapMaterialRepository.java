package com.example.maintenanceappv2.adaptors;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.Database.AppDatabase;
import com.example.maintenanceappv2.Database.SapMaterialDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SapMaterialRepository {

    private final SapMaterialDao sapMaterialDao;
    private final ExecutorService executorService;

    public SapMaterialRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "sap_local_database")
                .fallbackToDestructiveMigration()
                .build();
        sapMaterialDao = db.sapMaterialDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insertAll(List<SapMaterialSearchModel> materials) {
        executorService.execute(() -> sapMaterialDao.insertAll(materials));
    }

    public LiveData<List<SapMaterialSearchModel>> searchMaterials(String query) {
        return sapMaterialDao.searchMaterials(query);
    }

    public LiveData<List<SapMaterialSearchModel>> searchMaterials(String query, String column) {
        return sapMaterialDao.searchMaterials(query, column);
    }

    public LiveData<Integer> getLocalDatabaseCount() {
        return sapMaterialDao.getCount();
    }
}



