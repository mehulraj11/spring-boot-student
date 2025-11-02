package com.example.student.dto;

public class AddStudentDto {
    private String name;
    private String registration_no;
    private String email;
    private String course;

    public AddStudentDto() {
    }

    public AddStudentDto(String name, String course, String email, String registration_no) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.registration_no = registration_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistrationNo(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
