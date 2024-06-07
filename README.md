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

**API Documentation**

Access the Swagger UI for detailed API documentation and testing:
http://localhost:8080/swagger-ui.html

**Endpoints**
Upload CSV File
Upload a CSV file containing student records to determine their scholarship eligibility.

URL: /students/upload
Method: POST
Content-Type: multipart/form-data
Parameters:
file (MultipartFile) - The CSV file to upload.
science (float) - Science criteria for eligibility.
maths (float) - Maths criteria for eligibility.
english (float) - English criteria for eligibility.
computer (float) - Computer criteria for eligibility.

curl -X POST "http://localhost:8080/students/upload" -H "Content-Type: multipart/form-data" -F "file=@students.csv" -F "science=85" -F "maths=90" -F "english=88" -F "computer=92"

Example Response
{
  "message": "File uploaded and data processed successfully"
}


Get Eligibility Status by Roll Number
Retrieve the scholarship eligibility status of a student by their roll number.

URL: /students/eligible/{rollNumber}
Method: GET
Response: JSON object of the student.
Example Request
bash
Copy code
curl -X GET "http://localhost:8080/students/eligible/1"
Example Response
json
Copy code
{
  "rollNumber": 1,
  "studentName": "John Doe",
  "science": 85.0,
  "maths": 90.0,
  "english": 88.0,
  "computer": 92.0,
  "eligible": "YES"
}
Get All Student Records
Retrieve all student records along with their scholarship eligibility status.

URL: /students/eligible
Method: GET
Response: JSON array of students.
Example Request
bash
Copy code
curl -X GET "http://localhost:8080/students/eligible"
Example Response
json
Copy code
[
  {
    "rollNumber": 1,
    "studentName": "John Doe",
    "science": 85.0,
    "maths": 90.0,
    "english": 88.0,
    "computer": 92.0,
    "eligible": "YES"
  },
  {
    "rollNumber": 2,
    "studentName": "Jane Smith",
    "science": 75.0,
    "maths": 80.0,
    "english": 78.0,
    "computer": 85.0,
    "eligible": "NO"
  }
]
Download Student Records as CSV
Download all student records and their scholarship eligibility status as a CSV file.

URL: /students/download
Method: GET
Response: CSV file.
Example Request
bash
Copy code
curl -X GET "http://localhost:8080/students/download" -o students.csv
CSV File Format
The CSV file should have the following headers:

csv
Copy code
roll_number,student_name,science,maths,english,computer,Eligible
Example CSV
csv
Copy code
roll_number,student_name,science,maths,english,computer,Eligible
1,John Doe,85,90,88,95,
2,Jane Smith,75,80,78,85,
License
This project is licensed under the MIT License - see the LICENSE file for details.

Contributing
Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Commit your changes (git commit -am 'Add some feature').
Push to the branch (git push origin feature/your-feature).
Create a new Pull Request.
Contact
For any questions or inquiries, please contact:

Name: Your Name
Email: your.email@example.com



