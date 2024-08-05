package com.example.maintenanceappv2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;

import java.util.List;

@Dao
public interface SapMaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SapMaterialSearchModel> materials);

    @Query("SELECT * FROM materials WHERE description LIKE '%' || :query || '%'")
    LiveData<List<SapMaterialSearchModel>> searchMaterials(String query);

    @Query("SELECT * FROM materials WHERE vendor LIKE '%' || :query || '%'")
    LiveData<List<SapMaterialSearchModel>> searchVendor(String query);

    @Query("SELECT * FROM materials WHERE machine LIKE '%' || :query || '%'")
    LiveData<List<SapMaterialSearchModel>> searchMachine(String query);

    @RawQuery(observedEntities = SapMaterialSearchModel.class)
    LiveData<List<SapMaterialSearchModel>> searchMaterials(SupportSQLiteQuery query);

    @Query("SELECT COUNT(*) FROM materials")
    LiveData<Integer> getCount();

    // Method to build and execute the dynamic search query
    default LiveData<List<SapMaterialSearchModel>> searchMaterials(String keyword, String column) {
        String queryStr = "SELECT * FROM materials WHERE " + column + " LIKE '%' || ? || '%'";
        SupportSQLiteQuery query = new SimpleSQLiteQuery(queryStr, new Object[]{keyword});
        return searchMaterials(query);
    }

    }



