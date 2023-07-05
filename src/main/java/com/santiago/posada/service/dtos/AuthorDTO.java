package com.santiago.posada.service.dtos;

import com.santiago.posada.repository.model.ToDo;
import org.springframework.data.relational.core.mapping.Column;

import java.util.ArrayList;
import java.util.List;

public class AuthorDTO {


    private int id;
    private String name;

    private String lastName;

    private int age;

    private String email;

    private long phoneNumber;

    private List<ToDo> tasks;


    public AuthorDTO() {
        this.tasks = new ArrayList<>();
    }

    public AuthorDTO(int id, String name, String lastName, int age, String email, long phoneNumber, List<ToDo> tasks) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public List<ToDo> getTasks() {
        return tasks;
    }

    public void setTasks(List<ToDo> tasks) {
        this.tasks = tasks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
