package com.example.student.dto;

public class StudentDto {
    private Long id;
    private String name;
    private String registration_no;
    private String email;
    private String course;

    public StudentDto() {
    }

    public StudentDto(Long id, String name, String registrationNo, String email, String course) {
        this.id = id;
        this.name = name;
        this.registration_no = registrationNo;
        this.email = email;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setRegistration_no(String registration_no) {
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
