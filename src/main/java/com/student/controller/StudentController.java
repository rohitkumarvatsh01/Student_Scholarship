package com.student.controller;

import com.student.entity.Student;
import com.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@Tag(name = "StudentController", description = "Operations for managing student scholarship eligibility")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload CSV files with student records",
            description = "Upload multiple CSV files containing student details and process them to determine scholarship eligibility based on dynamic criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<?> uploadCSV(@RequestPart("file") MultipartFile file,
                                       @RequestParam float science,
                                       @RequestParam float maths,
                                       @RequestParam float english,
                                       @RequestParam float computer)
    {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\":\"File is empty\"}");
        }

        try {
            studentService.uploadCSV(file, science, maths, english, computer);
            return ResponseEntity.ok("{\"message\":\"File uploaded and data processed successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\":\"Error processing file\"}");
        }
    }

    @Operation(
            summary = "Get eligibility status by roll number",
            description = "Retrieve the scholarship eligibility status of a student by their roll number"
    )
    @GetMapping("/eligible/{rollNumber}")
    public ResponseEntity<?> getEligibility(@PathVariable Long rollNumber) {
        Optional<Student> student = studentService.getStudentByRollNumber(rollNumber);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(404).body("{\"message\":\"Student not found\"}");
        }
    }

    @Operation(
            summary = "Get all student records",
            description = "Retrieve all student records along with their scholarship eligibility status"
    )
    @GetMapping("/eligible")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @Operation(
            summary = "Download student records as CSV",
            description = "Download all student records and their scholarship eligibility status as a CSV file"
    )
    @GetMapping("/download")
    public ResponseEntity<?> downloadCSV() {
        try {
            ByteArrayInputStream csvData = studentService.generateCSVData();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.csv");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(new InputStreamResource(csvData));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("{\"message\":\"Error generating CSV file\"}");
        }
    }
}
