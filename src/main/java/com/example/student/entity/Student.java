package com.example.student.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private Users users;

    private String name;
    private LocalDate dob;
    public Student() {}
    public Student(Long studentId,
                   Users users,
                   String name,
                   LocalDate dob
    ) {
        this.studentId = studentId;
        this.users = users;
        this.name = name;
        this.dob = dob;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
