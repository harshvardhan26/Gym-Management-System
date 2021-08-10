/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Harshvardhan Kale
 */
public class Login {
    private String username;
    private char[] password;
    String designation;
    
    public Login(String username, char[] password, String designation) {
        this.username = username;
        this.password = password;
        this.designation = designation;
    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
