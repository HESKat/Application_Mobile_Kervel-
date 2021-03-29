package com.example.kervel.modele;

public class LoginModel {

    private String email, first_name, last_name,username;
    private int user_type;

    public LoginModel(String email, String first_name, String last_name, int user_type,String username) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_type() {
        return user_type;
    }
}


