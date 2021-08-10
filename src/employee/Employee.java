/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import java.util.Date;
import common.Person;

/**
 *
 * @author Harshvardhan Kale
 */
public class Employee extends Person{
    
    private Date doj;
    private String designation, houseNumber, street, city, zipcode;
    private char[] password;
    
    public Employee(String firstName, String lastName, String phone, String email, String gender,
            Date dob, Date doj, String designation, String branchName, char[] password, String houseNumber, String street, String city, String zipcode) {
        super(firstName, lastName, phone, email, gender, dob, branchName);
        this.doj = doj;
        this.designation = designation;
        this.password = password;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Date getDoj() {
        return doj;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getDesignation() {
        return designation;
    }

    public char[] getPassword() {
        return password;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
