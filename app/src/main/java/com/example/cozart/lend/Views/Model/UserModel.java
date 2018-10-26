package com.example.cozart.lend.Views.Model;

public class UserModel {

    private String lastname;
    private String firstname;
    private String mail;
    private String birthday;
    private String phone;
    private String city;

    public UserModel() {
    }

    public UserModel (String lastname, String firstname, String mail, String birthday, String phone, String city){
        this.lastname = lastname;
        this.firstname = firstname;
        this.mail = mail;
        this.birthday = birthday;
        this.phone = phone;
        this.city = city;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
