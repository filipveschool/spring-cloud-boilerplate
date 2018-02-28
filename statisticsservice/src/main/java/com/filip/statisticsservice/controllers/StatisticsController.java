package com.filip.statisticsservice.controllers;

import com.filip.statisticsservice.model.Account;
import com.filip.statisticsservice.model.timeseries.DataPoint;
import com.filip.statisticsservice.services.StatisticsService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/current")
    public List<DataPoint> getCurrentAccountStatistics(Principal principal) {
        return statisticsService.findByAccountName(principal.getName());
    }

    @PreAuthorize(value = "#oauth2.hasScope('server') or #accountName.equals('demo')")
    @GetMapping(value = "/{accountName}")
    public List<DataPoint> getStatisticsByAccountName(@PathVariable("accountName") String accountName) {
        return statisticsService.findByAccountName(accountName);
    }

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @PutMapping(value = "/{accountName}")
    public void saveAccountStatistics(@PathVariable("accountName") String accountName, @Valid @RequestBody Account account) {
        statisticsService.save(accountName, account);
    }


}
