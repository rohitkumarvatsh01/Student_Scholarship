package com.student;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Student Scholarship Eligibility Service",
				version = "1.0.0",
				description = "API for uploading and processing student records to determine scholarship eligibility"
		),
		servers = @Server(
				url = "http://localhost:8080",
				description = "Local development server"
		)
)
@SpringBootApplication
public class StudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}
}
