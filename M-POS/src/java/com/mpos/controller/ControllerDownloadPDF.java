package com.mpos.controller;

import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Katawut
 */
public class ControllerDownloadPDF extends ActionSupport {

    private String country;
    private String countrydata;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountrydata() {
        return countrydata;
    }

    public void setCountrydata(String countrydata) {
        this.countrydata = countrydata;
    }

    public String execute() throws IOException, DocumentException, JSONException {

        JSONArray jsonarray = new JSONArray(getCountrydata());

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); ++i) {
            JSONObject rec = jsonarray.getJSONObject(i);

            data.add(rec.getString("name"));
            data.add(rec.getString("lng"));
            data.add(rec.getString("lat"));
        }
        CreatePDF.mainPDF(getCountry(), data);
        return SUCCESS;
    }
}
