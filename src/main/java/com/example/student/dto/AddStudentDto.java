package com.example.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AddStudentDto {
    private Long userId;

    @NotBlank(message = "Name is Required")
    private String name;

    @NotNull(message = "DOB is required")
    private LocalDate dob;


    public AddStudentDto() {
    }
    public AddStudentDto(Long userId, String name, LocalDate dob) {
        this.userId = userId;
        this.name = name;
        this.dob = dob;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
