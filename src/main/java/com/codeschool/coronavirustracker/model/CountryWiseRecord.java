package com.codeschool.coronavirustracker.model;

import java.util.ArrayList;
import java.util.List;

public class CountryWiseRecord {

    private String country;
    private List<StateWiseRecord> stateWiseRecords;

    public CountryWiseRecord() {
    }

    public CountryWiseRecord(String country, List<StateWiseRecord> stateWiseRecords) {
        this.country = country;
        this.stateWiseRecords = new ArrayList<>(stateWiseRecords);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStateWiseRecords(List<StateWiseRecord> stateWiseRecords) {
        this.stateWiseRecords = stateWiseRecords;
    }

    public String getCountry() {
        return country;
    }

    public List<StateWiseRecord> getStateWiseRecords() {
        return stateWiseRecords;

            }
}
