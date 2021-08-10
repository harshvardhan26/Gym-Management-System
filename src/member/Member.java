/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import common.Person;
import java.util.Date;

/**
 *
 * @author Harshvardhan Kale
 */
public class Member extends Person {

    private int duration, advancePaid, height;
    private double bodyFat, weight;
    private Date startDate;
    private String injury;
    
    public Member(String firstName, String lastName, String phone, String email, String gender, Date dob, 
            int duration, Date startDate, String branchName, int amountPaid, int height, double weight, double bodyFat, String injury) {
        super(firstName, lastName, phone, email, gender, dob, branchName);
        this.duration = duration;
        this.startDate = startDate;
        this.advancePaid = amountPaid;
        this.height = height;
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.injury = injury;
    }

    public int getDuration() {
        return duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getAdvancePaid() {
        return advancePaid;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBodyFat() {
        return bodyFat;
    }

    public String getInjury() {
        return injury;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setAdvancePaid(int advancePaid) {
        this.advancePaid = advancePaid;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBodyFat(double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }
}
