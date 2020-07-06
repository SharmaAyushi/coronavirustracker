package com.codeschool.coronavirustracker.service;


import com.codeschool.coronavirustracker.model.CountryWiseRecord;
import com.codeschool.coronavirustracker.model.CovidLatestStats;
import com.codeschool.coronavirustracker.model.StateWiseRecord;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CoronavirusTrackerService {

    private static final String  DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private Supplier<CovidLatestStats> memoized = Suppliers.memoizeWithExpiration(this::coronavirusLatestStatsSupplier, 15L, TimeUnit.MINUTES);

    @Autowired
    private RestTemplate restTemplate;

    public CovidLatestStats getLatestStats() {
        return memoized.get();
    }

    private CovidLatestStats coronavirusLatestStatsSupplier() {
        List<CountryWiseRecord> countryWiseRecords = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.getForEntity(DATA_URL, String.class);

        Multimap<String, StateWiseRecord> stateWiseRecords = parseCsvResponse(response);
        for (String country: stateWiseRecords.keys()) {
            CountryWiseRecord countryWiseRecord = new CountryWiseRecord(country, new ArrayList<>(stateWiseRecords.get(country)));
            countryWiseRecords.add(countryWiseRecord);
        }
        return new CovidLatestStats(countryWiseRecords);
    }

    private Multimap<String, StateWiseRecord> parseCsvResponse(ResponseEntity<String> response) {
        StringReader reader = new StringReader(response.getBody());
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing csv records", e);
        }
        Multimap<String, StateWiseRecord> stateWiseRecords = ArrayListMultimap.create();
        for (CSVRecord record : records) {
            String country = record.get("Country/Region");
            String state = record.get("Province/State");
            String confirmedCases = record.get(record.size()-1);

            StateWiseRecord stateWiseRecord = new StateWiseRecord(state, Long.parseLong(confirmedCases));
            stateWiseRecords.put(country, stateWiseRecord);

        }
        return stateWiseRecords;
    }

}
