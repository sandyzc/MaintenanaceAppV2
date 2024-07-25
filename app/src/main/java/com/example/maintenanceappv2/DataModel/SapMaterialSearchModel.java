package com.example.maintenanceappv2.DataModel;

public class SapMaterialSearchModel {

String description,location,machine,station,sub_assembly,gen_desc,catergory,image,used_in_1,used_in_2,used_in_3,used_in_4,vendor;
int sap_code,stock;


    public SapMaterialSearchModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getSub_assembly() {
        return sub_assembly;
    }

    public void setSub_assembly(String sub_assembly) {
        this.sub_assembly = sub_assembly;
    }

    public String getGen_desc() {
        return gen_desc;
    }

    public void setGen_desc(String gen_desc) {
        this.gen_desc = gen_desc;
    }


    public String getCatergory() {
        return catergory;
    }

    public void setCatergory(String catergory) {
        this.catergory = catergory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsed_in_1() {
        return used_in_1;
    }

    public void setUsed_in_1(String used_in_1) {
        this.used_in_1 = used_in_1;
    }

    public String getUsed_in_2() {
        return used_in_2;
    }

    public void setUsed_in_2(String used_in_2) {
        this.used_in_2 = used_in_2;
    }

    public String getUsed_in_3() {
        return used_in_3;
    }

    public void setUsed_in_3(String used_in_3) {
        this.used_in_3 = used_in_3;
    }

    public String getUsed_in_4() {
        return used_in_4;
    }

    public void setUsed_in_4(String used_in_4) {
        this.used_in_4 = used_in_4;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getSap_code() {
        return sap_code;
    }

    public void setSap_code(int sap_code) {
        this.sap_code = sap_code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
