package com.codeschool.coronavirustracker.model;

public class StateWiseRecord {

    private String state;
    private Long confirmedCases;

    public StateWiseRecord(String state, Long cases) {
        this.state = state;
        this.confirmedCases = cases;
    }

    public String getState() {
        return state;
    }

    public Long getConfirmedCases() {
        return confirmedCases;
    }
}
