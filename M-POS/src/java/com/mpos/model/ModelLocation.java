package com.mpos.model;

public class ModelLocation {

    private String Name;
    private double Lat;
    private double Lng;
    private String ISO;
    private String Province;
    
    //CONSTRUCTOR
    public ModelLocation() {
    }

    public ModelLocation(String Name, double Lat, double Lng, String ISO, String Province) {
        this.Name = Name;
        this.Lat = Lat;
        this.Lng = Lng;
        this.ISO = ISO;
        this.Province = Province;
    }
    
    // SET GET name
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    // SET GET lat
    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public double getLat() {
        return Lat;
    }
    
    // SET GET lng
    public void setLng(double Lng) {
        this.Lng = Lng;
    }

    public double getLng() {
        return Lng;
    }
    
    // SET GET iso
    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getISO() {
        return ISO;
    }
    
    // SET GET province
    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getProvince() {
        return Province;
    }
}
