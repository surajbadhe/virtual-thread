# Concurrent Threads Performance Benchmark (Tomcat vs Platform vs Virtual Threads)

## Overview

This project benchmarks the performance of three threading models in Java:

- **Tomcat default threads**
- **Java Platform threads** (`ExecutorService`)
- **Java Virtual Threads** (`Project Loom`)

The aim is to measure latency, throughput, and error rate under concurrent load scenarios using JMeter.

## Table of Contents

- Overview
- Project Setup
- Service Implementation
- Running the Application
- API Endpoints
- Load Testing with JMeter
- Benchmark Results
- Performance Metrics Explained
- Conclusion
- Files


## Project Setup

**Tech Stack**

- **Java 21** (required for Virtual Threads)
- **Spring Boot**
- **H2 Database** (in-memory, pre-filled with 1000 test records)
- **JMeter** (for load testing)
- **CSV Report Generator** (for benchmarking results).[^3]


## Service Implementation

Three Spring services are provided:

- `ReportService` — Uses **Tomcat default threads**
- `PlatformReportService` — Uses `Executors.newFixedThreadPool(5)` (**Platform threads**)
- `VirtualReportService` — Uses `Executors.newVirtualThreadPerTaskExecutor()` (**Virtual threads**)

Each service:

- Fetches records from H2 DB
- Generates a CSV report for a region as requested


## Running the Application

To run the application:

```sh
mvn spring-boot:run
```

Requirements:

- Java 21+
- Maven


## API Endpoints

- `POST /report/tomcat/{region}`   — Tomcat threads
- `POST /report/platform/{region}` — Platform threads
- `POST /report/virtual/{region}`  — Virtual threads

Example:

- `GET /report/tomcat/US`


## Load Testing with JMeter

Steps:

1. Launch JMeter:
    - Linux/Mac: `./jmeter.sh`
    - Windows: `jmeter.bat`
2. Create a Thread Group:
    - **Number of Threads**: 1000
    - **Ramp-Up Period**: 10
    - **Loop Count**: 1
3. Add an **HTTP Request Sampler**:
    - **Method**: POST
    - **Path**: `/report/{region}` (or other endpoints)
4. Add Listeners:
    - Aggregate Report
    - Summary Report
    - Graph Results
5. Run the test and export results as CSV.


### Observations

- **Tomcat default threads**: Failed under high concurrency; all requests dropped.
- **Platform threads**: Low latency, performed well.
- **Virtual threads**: Predictable, stable performance with similar throughput and lower variation.


## Performance Metrics Explained

- **Latency**: Time to first response byte. Lower is better.
- **Throughput**: Number of serviced requests per second. Higher is better.
- **Error %**: Percentage of failed requests. Lower is more reliable.
- **Std. Deviation**: Variance in response times. Lower indicates consistency.


## Conclusion

- **Tomcat default threads** are unsuitable for high-concurrency workloads.
- **Platform threads** work well, but are limited by thread pool size.
- **Virtual threads** (Project Loom) deliver scalability and simplicity, making them ideal for highly concurrent applications.