package com.student.service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void saveStudents(List<Student> students) {
        studentRepository.saveAll(students);
    }

    public Optional<Student> getStudentByRollNumber(String rollNumber) {
        return studentRepository.findById(rollNumber);
    }
}
