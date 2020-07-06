package com.codeschool.coronavirustracker.controller;

import com.codeschool.coronavirustracker.model.CountryWiseRecord;
import com.codeschool.coronavirustracker.model.CovidLatestStats;
import com.codeschool.coronavirustracker.service.CoronavirusTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("coronavirustracker")
public class CoronavirusTrackerController {

    Logger logger = LoggerFactory.getLogger(CoronavirusTrackerController.class);

    @Autowired
    private CoronavirusTrackerService coronavirusTrackerService;

    @GetMapping("/getLatestStats")
    public CovidLatestStats getLatestStats() {
        try{
            return coronavirusTrackerService.getLatestStats();
        } catch (Exception exception) {
            logger.debug("Error while calling CoronavirusTrackerService",exception);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Please try again after some time");
        }

    }
}
