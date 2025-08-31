package com.suraj.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.suraj.demo.entity.Customer;
import com.suraj.demo.repo.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataLoadingService {

    private final CustomerRepository customerRepository;

    public void loadCsvData() {
        // Use the correct file name here
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/sample_data_report.csv")))) {
            List<Customer> customers = reader.lines()
                    .skip(1) // Skip the header row
                    .map(line -> {
                        String[] fields = line.split(",");
                        Long id = Long.parseLong(fields[0].trim());
                        String name = fields[1].trim();
                        String email = fields[2].trim();
                        String gender = fields[3].trim();
                        String region = fields[4].trim();
                        return new Customer(id, name, email, gender, region);
                    })
                    .collect(Collectors.toList());

            customerRepository.saveAll(customers);
        } catch (Exception e) {
            System.err.println("Error loading CSV data: " + e.getMessage());
        }
    }
}
