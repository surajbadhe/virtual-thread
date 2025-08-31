package com.suraj.demo.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.suraj.demo.service.DataLoadingService;

@Component
public class DataLoader implements CommandLineRunner {

    private final DataLoadingService dataLoadingService;

    public DataLoader(DataLoadingService dataLoadingService) {
        this.dataLoadingService = dataLoadingService;
    }

    @Override
    public void run(String... args) throws Exception {
        dataLoadingService.loadCsvData();
    }
}