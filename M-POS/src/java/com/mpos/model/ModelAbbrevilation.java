package com.mpos.model;
public class ModelAbbrevilation {
    private String Country;
    private String ISO;
    
    //CONSTRUCTOR
    public ModelAbbrevilation() {
    }

    public ModelAbbrevilation(String Country, String ISO) {
        this.Country = Country;
        this.ISO = ISO;
    }
    
    // SET GET country
    public void setCountry(String Country) {
        this.Country = Country;
    }
    
    public String getCountry() {
        return Country;
    }
    
    // SET GET iso
    public void setISO(String ISO) {
        this.ISO = ISO;
    }
    
    public String getISO() {
        return ISO;
    }
}
