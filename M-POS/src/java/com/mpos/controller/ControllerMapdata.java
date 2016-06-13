package com.mpos.controller;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ControllerMapdata extends ActionSupport {
    
    private static final long serialVersionUID = 1L;
    
    private String location, json;
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getJson() {
        return json;

    }

    public void setJson(String successMessage) {
        this.json = successMessage;
    }
    
    public String execute(){
        //QUERY 
        ArrayList<String> data = ControllerSelect.selector(getLocation());
        int n = data.size();
        System.out.println(n);
            // SET JSON
        JSONObject obj[] = new JSONObject[n];
       JSONArray jsonArray = new JSONArray();
       try
       {
           int i=0;
           int j=0;
           while(j<(n/5))
           {
               
                obj[j] = new JSONObject();
               
                obj[j].put("name", data.get(i));
                i++;
                        
                obj[j].put("lat", data.get(i));
                i++;
                obj[j].put("lng", data.get(i));
                i++;
                
                obj[j].put("iso", data.get(i));
                i++;
                
                obj[j].put("province", data.get(i));
                i++;
                
                jsonArray.put(obj[j]);
                j++;
                
           }
           
       }
       catch(JSONException e){}
        
        setJson(jsonArray.toString());
        return SUCCESS;
    }
}
