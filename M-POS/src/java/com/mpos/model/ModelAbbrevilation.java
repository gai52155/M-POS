/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpos.model;
public class ModelAbbrevilation {
    private int abbrevilation_id;
    private String country;
    private String iso;
    
    public ModelAbbrevilation() {
    }

    public ModelAbbrevilation(int abbrevilation_id, String name, String country, String iso) {
        this.abbrevilation_id = abbrevilation_id;
        this.country = country;
        this.iso = iso;
    }

    //SET GET ID
    public void setabbrevilation_id(int abbrevilation_id) {
        this.abbrevilation_id = abbrevilation_id;
    }

    public int getabbrevilation_id() {
        return abbrevilation_id;
    }
    
    // SET GET country
    public void setcountry(String country) {
        this.country = country;
    }
    
    public String getcountry() {
        return country;
    }
    
    // SET GET iso
    public void setiso(String iso) {
        this.iso = iso;
    }
    
    public String getiso() {
        return iso;
    }
}
