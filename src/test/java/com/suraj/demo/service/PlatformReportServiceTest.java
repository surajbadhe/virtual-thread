package com.suraj.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.suraj.demo.entity.Customer;
import com.suraj.demo.repo.CustomerRepository;

@ExtendWith({MockitoExtension.class})
public class PlatformReportServiceTest {
	
	@Mock
	private CustomerRepository repository;
	
	@InjectMocks
	private PlatformReportService platformReportService;
	@Mock 
	private Executors executors;
	
	List<Customer> customers;
	
	@BeforeEach
	 void setup() {
		
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/sample_data_report.csv")))) {
        customers = reader.lines()
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

        
    } catch (Exception e) {
        System.err.println("Error loading CSV data: " + e.getMessage());
    }
	}
	
	@Test
	void generateReportForRegionUS() {
		when(repository.findByRegion("US")).thenReturn(customers);
		platformReportService.generateReportForRegion("US");
		assertNotEquals(customers, null);
	}
}
