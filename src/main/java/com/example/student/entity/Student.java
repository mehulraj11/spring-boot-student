package com.example.student.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "usersId", nullable = false, unique = true)
    private Users users;

    private String name;
    private LocalDate dob;
//    private String doc;
    public Student() {
    }

    public Student(Long id, Users users, String name, LocalDate dob) {
        this.id = id;
        this.users = users;
        this.name = name;
        this.dob = dob;
//        this.doc = doc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public String getDoc() {
//        return doc;
//    }
//
//    public void setDoc(String doc) {
//        this.doc = doc;
//    }
}
