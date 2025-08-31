package com.suraj.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.demo.service.PlatformReportService;
import com.suraj.demo.service.ReportService;
import com.suraj.demo.service.VirtualReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private PlatformReportService platformReportService;

	@Autowired
	private VirtualReportService virtualReportService;

	@PostMapping("/tomcat/{region}")
	public ResponseEntity<String> generateReport(@PathVariable String region) {
		reportService.generateReportForRegion(region);
		return ResponseEntity.ok("✅ Tomcat report started for region: " + region);
	}

	@PostMapping("/platform/{region}")
	public ResponseEntity<String> generateReportPlatform(@PathVariable String region) {
		platformReportService.generateReportForRegion(region);
		return ResponseEntity.ok("✅ Platform report started for region: " + region);
	}

	@PostMapping("/virtual/{region}")
	public ResponseEntity<String> generateReportVirtual(@PathVariable String region) {
		virtualReportService.generateReportForRegion(region);
		return ResponseEntity.ok("✅ Virtual report started for region: " + region);
	}

}
