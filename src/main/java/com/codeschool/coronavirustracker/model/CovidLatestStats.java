package com.codeschool.coronavirustracker.model;

import java.util.List;

public class CovidLatestStats {
    private List<CountryWiseRecord> records;

    public CovidLatestStats() {
    }

    public CovidLatestStats(List<CountryWiseRecord> records) {
        this.records = records;
    }

    public List<CountryWiseRecord> getRecords() {
        return records;
    }

    public void setRecords(List<CountryWiseRecord> records) {
        this.records = records;
    }
}
