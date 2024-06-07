package com.student.service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void uploadCSV(MultipartFile file, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) throws IOException {
        List<Student> students = parseCSVFile(file.getInputStream(), scienceCriteria, mathsCriteria, englishCriteria, computerCriteria);
        saveStudents(students);
    }

    public void saveStudents(List<Student> students) {
        for (Student student : students) {
            Optional<Student> existStudent = studentRepository.findById(student.getRollNumber());
            if (existStudent.isPresent()) {
                Student updateStudent = existStudent.get();
                updateStudent.setStudentName(student.getStudentName());
                updateStudent.setScience(student.getScience());
                updateStudent.setMaths(student.getMaths());
                updateStudent.setEnglish(student.getEnglish());
                updateStudent.setComputer(student.getComputer());
                updateStudent.setEligible(student.getEligible());

                studentRepository.save(updateStudent);
            } else {
                studentRepository.save(student);
            }
        }
    }

    public Optional<Student> getStudentByRollNumber(Long rollNumber) {
        return studentRepository.findById(rollNumber);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    private List<Student> parseCSVFile(InputStream inputStream, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) throws IOException {
        List<Student> students = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader("roll_number", "student_name", "science", "maths", "english", "computer", "Eligible")
                    .withFirstRecordAsHeader()
                    .parse(reader);
            for (CSVRecord record : parser) {
                Student student = new Student();
                student.setRollNumber(Long.parseLong(record.get("roll_number")));
                student.setStudentName(record.get("student_name"));
                student.setScience(Float.parseFloat(record.get("science")));
                student.setMaths(Float.parseFloat(record.get("maths")));
                student.setEnglish(Float.parseFloat(record.get("english")));
                student.setComputer(Float.parseFloat(record.get("computer")));
                student.setEligible(checkEligibility(student, scienceCriteria, mathsCriteria, englishCriteria, computerCriteria) ? "YES" : "NO");
                students.add(student);
            }
        }
        return students;
    }

    private boolean checkEligibility(Student student, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) {
        return student.getScience() > scienceCriteria && student.getMaths() > mathsCriteria && student.getEnglish() > englishCriteria && student.getComputer() > computerCriteria;
    }

    public ByteArrayInputStream generateCSVData() throws IOException {
        List<Student> students = getAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT
                .withHeader("roll_number", "student_name", "science", "maths", "english", "computer", "Eligible"))) {
            for (Student student : students) {
                printer.printRecord(student.getRollNumber(), student.getStudentName(), student.getScience(),
                        student.getMaths(), student.getEnglish(), student.getComputer(), student.getEligible());
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
