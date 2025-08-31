package com.suraj.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suraj.demo.entity.Customer;
import com.suraj.demo.repo.CustomerRepository;
import com.suraj.demo.utils.CsvReportUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportService {
	
	@Autowired
	public CustomerRepository repository;
	
	// 300
	// tomcat default thread 200
	// 200 request processing
	// 100 request waiting in queue
    public void generateReportForRegion(String region) {

        log.info("Generating report for region: {} | {}", region, Thread.currentThread());

        List<Customer> customers = repository.findByRegion(region);//1
        try {
            CsvReportUtil.writeCustomersToCsv("simple_" + region, customers);//2
        } catch (Exception e) {
            System.out.println("❌ Error writing report for region: " + region);
        }

    }
}
