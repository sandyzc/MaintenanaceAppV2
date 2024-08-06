package com.example.maintenanceappv2;

import android.app.Application;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;


public class Algolia_app extends Application {

    private Client algoliaClient;
    private Index index;
    private final String appid= "W804M83I49";
    private final String adminkey= "0d375b223644080e657a532509e4e79e";
    private final String indexname= "material desc";


    @Override
    public void onCreate() {
        super.onCreate();

        algoliaClient = new Client(appid, adminkey);
        index = algoliaClient.getIndex(indexname);
    }

    public Index getIndex() {
        return index;
    }
}