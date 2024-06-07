# Student Scholarship Eligibility Service

## Overview

The Student Scholarship Eligibility Service is a Spring Boot application designed to upload, process, and manage student records to determine scholarship eligibility based on specified criteria. It supports CSV file uploads, dynamic eligibility criteria, and provides various endpoints to interact with student data.

## Features

- **CSV Upload**: Upload CSV files containing student records.
- **Dynamic Criteria**: Determine scholarship eligibility based on user-defined criteria.
- **API Endpoints**: Access student records and eligibility status via RESTful APIs.
- **CSV Download**: Download all student records and their eligibility status as a CSV file.
- **Swagger Integration**: API documentation and testing through Swagger UI.

## Technologies Used

- Java 17
- Spring Boot 3.3.0
- Spring Data JPA
- Hibernate
- MySQL
- Apache Commons CSV
- Swagger OpenAPI

## API Documentation

Access the Swagger UI for detailed API documentation and testing:
http://localhost:8080/swagger-ui.html

## Endpoints

### Upload CSV File

Upload a CSV file containing student records to determine their scholarship eligibility.

- **URL**: /students/upload
- **Method**: POST
- **Content-Type**: multipart/form-data
- **Parameters**:
  - `file` (MultipartFile) - The CSV file to upload.
  - `science` (float) - Science criteria for eligibility.
  - `maths` (float) - Maths criteria for eligibility.
  - `english` (float) - English criteria for eligibility.
  - `computer` (float) - Computer criteria for eligibility.
