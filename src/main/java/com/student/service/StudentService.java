package com.student.service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static final int THREAD_COUNT = 8;

    public void uploadCSV(MultipartFile file, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) throws IOException, InterruptedException, ExecutionException {
        logger.info("Received file for processing: {}", file.getOriginalFilename());

        List<Student> students = parseCSVFile(file.getInputStream(), scienceCriteria, mathsCriteria, englishCriteria, computerCriteria);
        saveStudents(students);

        logger.info("File processed and students saved successfully");
    }

    public void saveStudents(List<Student> students) {
        studentRepository.saveAll(students);
        logger.info("Saved {} student records to the database", students.size());
    }

    public Optional<Student> getStudentByRollNumber(Long rollNumber) {
        logger.info("Fetching student record for roll number: {}", rollNumber);
        return studentRepository.findById(rollNumber);
    }

    public List<Student> getAll() {
        logger.info("Fetching all student records");
        return studentRepository.findAll();
    }

    private List<Student> parseCSVFile(InputStream inputStream, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) throws IOException, InterruptedException, ExecutionException {
        logger.info("Starting to parse CSV file");

        List<Student> students = new ArrayList<>();
        List<CSVRecord> records;

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader("roll_number", "student_name", "science", "maths", "english", "computer", "Eligible")
                    .withFirstRecordAsHeader()
                    .parse(reader);
            records = parser.getRecords();
            logger.info("Parsed {} records from the CSV file", records.size());
        }

        int chunkSize = records.size() / THREAD_COUNT;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<List<Student>>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * chunkSize;
            int end = (i == THREAD_COUNT - 1) ? records.size() : (i + 1) * chunkSize;
            List<CSVRecord> chunk = records.subList(start, end);

            Callable<List<Student>> task = () -> {
                logger.info("Processing chunk from {} to {}", start, end);
                return processChunk(chunk, scienceCriteria, mathsCriteria, englishCriteria, computerCriteria);
            };
            futures.add(executor.submit(task));
        }

        for (Future<List<Student>> future : futures) {
            try {
                students.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error processing chunk", e);
                throw e;
            }
        }

        executor.shutdown();
        logger.info("Finished parsing CSV file");
        return students;
    }

    private List<Student> processChunk(List<CSVRecord> chunk, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) {
        List<Student> students = new ArrayList<>();
        for (CSVRecord record : chunk) {
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
        logger.debug("Processed chunk size: {}", students.size());
        return students;
    }

    private boolean checkEligibility(Student student, float scienceCriteria, float mathsCriteria, float englishCriteria, float computerCriteria) {
        boolean isEligible = student.getScience() > scienceCriteria && student.getMaths() > mathsCriteria && student.getEnglish() > englishCriteria && student.getComputer() > computerCriteria;
        logger.debug("Eligibility for student {}: {}", student.getRollNumber(), isEligible ? "YES" : "NO");
        return isEligible;
    }

    public ByteArrayInputStream generateCSVData() throws IOException {
        logger.info("Generating CSV data for all student records");

        List<Student> students = getAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT
                .withHeader("roll_number", "student_name", "science", "maths", "english", "computer", "Eligible"))) {
            for (Student student : students) {
                printer.printRecord(student.getRollNumber(), student.getStudentName(), student.getScience(),
                        student.getMaths(), student.getEnglish(), student.getComputer(), student.getEligible());
            }
        }

        logger.info("CSV data generated successfully");
        return new ByteArrayInputStream(out.toByteArray());
    }
}
