package com.student.controller;

import com.student.entity.Student;
import com.student.service.StudentService;
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
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file,
                                       @RequestParam float science,
                                       @RequestParam float maths,
                                       @RequestParam float english,
                                       @RequestParam float computer) {
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


    @GetMapping("/eligible/{rollNumber}")
    public ResponseEntity<?> getEligibility(@PathVariable Long rollNumber) {
        Optional<Student> student = studentService.getStudentByRollNumber(rollNumber);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(404).body("{\"message\":\"Student not found\"}");
        }
    }

    @GetMapping("/eligible")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

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
