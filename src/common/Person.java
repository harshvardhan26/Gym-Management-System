package common;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harshvardhan Kale
 */
public abstract class Person {
    
    private String firstName, lastName, phone, email, gender, branchName;
    private Date dob;
    
    public Person(String firstName, String lastName, String phone, String email, String gender, Date dob, String branchName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.branchName = branchName;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
    
    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }    

    public Date getDob() {
        return dob;
    }

    public String getBranchName() {
        return branchName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
