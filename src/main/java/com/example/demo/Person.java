package com.example.demo;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private int id;
    private String firstname;
    private String lastname;
    private String age;


    public Person(int idstudent, String firstname, String lastname, String age){
        this.id = idstudent;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public int getId(){
        return id;
    }

    public void setId(int idstudent) {
        this.id = idstudent;
    }

    public String getFirstname(){
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge(){
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
