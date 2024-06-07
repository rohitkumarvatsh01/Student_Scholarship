package com.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private Long rollNumber;
    private String studentName;
    private float science;
    private float maths;
    private float english;
    private float computer;
    private String eligible;

    public Student() {

    }

    public Long getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Long rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getScience() {
        return science;
    }

    public void setScience(float science) {
        this.science = science;
    }

    public float getMaths() {
        return maths;
    }

    public void setMaths(float maths) {
        this.maths = maths;
    }

    public float getEnglish() {
        return english;
    }

    public void setEnglish(float english) {
        this.english = english;
    }

    public float getComputer() {
        return computer;
    }

    public void setComputer(float computer) {
        this.computer = computer;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    public Student(Long rollNumber, String studentName, float science, float maths, float english, float computer, String eligible) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.science = science;
        this.maths = maths;
        this.english = english;
        this.computer = computer;
        this.eligible = eligible;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", studentName='" + studentName + '\'' +
                ", science=" + science +
                ", maths=" + maths +
                ", english=" + english +
                ", computer=" + computer +
                ", eligible='" + eligible + '\'' +
                '}';
    }
}