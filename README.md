# Student Scholarship Eligibility Service

## Overview

The Student Scholarship Eligibility Service is a Spring Boot application designed to upload, process, and manage student records to determine scholarship eligibility based on specified criteria. It supports CSV file uploads, dynamic eligibility criteria, and provides various endpoints to interact with student data.

## Features

- *CSV Upload*: Upload CSV files containing student records.
- *Dynamic Criteria*: Determine scholarship eligibility based on user-defined criteria.
- *API Endpoints*: Access student records and eligibility status via RESTful APIs.
- *CSV Download*: Download all student records and their eligibility status as a CSV file.
- *Swagger Integration*: API documentation and testing through Swagger UI.


## API Documentation

This application integrates Swagger for API documentation and testing. You can access the Swagger UI by visiting the following URL: Swagger UI.
http://localhost:8080/swagger-ui.html

## Endpoints

### 1 Upload CSV File

Upload a CSV file containing student records to determine their scholarship eligibility.

- *URL*: /students/upload
- *Method*: POST
- *Content-Type*: multipart/form-data
- *Parameters*:
  - `file` (MultipartFile) - The CSV file to upload.
  - science (float) - Science criteria for eligibility.
  - maths (float) - Maths criteria for eligibility.
  - english (float) - English criteria for eligibility.
  - computer (float) - Computer criteria for eligibility.

Example Request:
 - POST: http://localhost:8080/students/upload

### 2 Get Eligibility Status by Roll Number

Retrieve the scholarship eligibility status of a student by their roll number.

- *URL*: /students/eligible/{rollNumber}
- *Method*: GET
- *Response*: JSON object of the student.

Example Request:
 - GET: http://localhost:8080/students/eligible/100101

### 3 Get All Student Records

Retrieve all student records along with their scholarship eligibility status.

- *URL*: /students/eligible
- *Method*: GET
- *Response*: JSON array of students.

Example Request:
- GET: http://localhost:8080/students/eligible

### 4 Download Student Records as CSV

Download all student records and their scholarship eligibility status as a CSV file.

- *URL*: /students/download
- *Method*: GET
- *Response*: CSV file.

Example Request:
- GET http://localhost:8080/students/download
- File Name:- students.csv

## Technologies Used

- Java 17
- Spring Boot 3.3.0
- Spring Data JPA
- Hibernate
- MySQL
- Apache Commons CSV
- Swagger OpenAPI
