package com.example.maintenanceappv2.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;

@Database(entities = {SapMaterialSearchModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SapMaterialDao sapMaterialDao();
}

