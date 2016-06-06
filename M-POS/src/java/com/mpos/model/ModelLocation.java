package com.mpos.model;

public class ModelLocation {

    private int location_id;
    private String name;
    private double lat;
    private double lng;
    private String iso;
    private String province;
    

    public ModelLocation() {
    }

    public ModelLocation(int location_id, String name, String adminPassword, double lat, 
                         double lng,String iso, String province) {
        this.location_id = location_id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.iso = iso;
        this.province = province;
    }

    //SET GET ID
    public void setlocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getlocation_id() {
        return location_id;
    }
    
    // SET GET name
    public void setname(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }

    // SET GET lat
    public void setlat(double lat) {
        this.lat = lat;
    }

    public double getlat() {
        return lat;
    }
    
    // SET GET lng
    public void setlng(double lng) {
        this.lng = lng;
    }

    public double getlng() {
        return lng;
    }
    
    // SET GET iso
    public void setiso(String iso) {
        this.iso = iso;
    }

    public String getiso() {
        return iso;
    }
    
    // SET GET province
    public void setprovince(String province) {
        this.province = province;
    }

    public String getprovince() {
        return province;
    }
}
