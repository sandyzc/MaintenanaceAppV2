package com.example.maintenanceappv2.DataModel;

public class User_Info {

    String name;
    String uid;

    public User_Info() {
    }

    public User_Info(String name,String uid) {
        this.name = name;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
