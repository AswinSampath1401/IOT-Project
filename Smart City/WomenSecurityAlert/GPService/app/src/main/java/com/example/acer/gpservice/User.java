package com.example.acer.gpservice;

public class User {

    int id;
    String name;
    String pN1;
    String pN2;
    String pN3;

    public User(int id, String name, String pN1, String pN2, String pN3) {
        this.id = id;
        this.name = name;
        this.pN1 = pN1;
        this.pN2 = pN2;
        this.pN3 = pN3;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getpN1() {
        return pN1;
    }

    public String getpN2() {
        return pN2;
    }

    public String getpN3() {
        return pN3;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setpN1(String pN1) {
        this.pN1 = pN1;
    }

    public void setpN2(String pN2) {
        this.pN2 = pN2;
    }

    public void setpN3(String pN3) {
        this.pN3 = pN3;
    }
}
