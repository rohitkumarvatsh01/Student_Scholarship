package com.student.controller;

import com.student.entity.Student;
import com.student.service.StudentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            List<Student> students = parseCSVFile(file.getInputStream());
            studentService.saveStudents(students);
            return ResponseEntity.ok("File uploaded and data processed successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
        }
    }

    @GetMapping("/eligible/{rollNumber}")
    public ResponseEntity<String> getEligibility(@PathVariable String rollNumber) {
        Optional<Student> student = studentService.getStudentByRollNumber(rollNumber);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get().getEligible());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }


    private List<Student> parseCSVFile(InputStream inputStream) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader("roll_number", "student_name", "science", "maths", "english", "computer", "Eligible")
                    .withFirstRecordAsHeader()
                    .parse(reader);
            for (CSVRecord record : parser) {
                Student student = new Student();
                student.setRollNumber(record.get("roll_number"));
                student.setStudentName(record.get("student_name"));
                student.setScience(Integer.parseInt(record.get("science")));
                student.setMaths(Integer.parseInt(record.get("maths")));
                student.setEnglish(Integer.parseInt(record.get("english")));
                student.setComputer(Integer.parseInt(record.get("computer")));
                student.setEligible(checkEligibility(student) ? "YES" : "NO");
                students.add(student);
            }
        }
        return students;
    }

    private boolean checkEligibility(Student student) {
        return student.getScience() > 85 && student.getMaths() > 90 && student.getEnglish() > 75 && student.getComputer() > 95;
    }
}
