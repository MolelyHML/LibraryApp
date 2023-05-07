package com.prockopev.libraryapp.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Person {

    private int personId;
    @NotEmpty(message = "ФИО не должно быть пустым")
    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public Person(int personId, String fullName, Date birthDate) {
        this.personId = personId;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
