/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Harshvardhan Kale
 */
public class LoginDAO {
    
    Connection conn;
    
    public LoginDAO() {
        conn = DBConnect.getConnection();
    }
    
    public boolean verifyLogin(Login login) {
        try {
            String username = login.getUsername();
            char[] password = login.getPassword();
            String designation = login.getDesignation();
            
            String getCredentialsQuery = "select email, password, title from employee e inner join employee_designation ed"
                    + " on e.employee_id = ed.employee_id inner join designation d on ed.designation_id = d.designation_id"
                    + " where email = ? and password = ? and title = ?";
            PreparedStatement getCredentialsStmt = conn.prepareStatement(getCredentialsQuery);
            getCredentialsStmt.setString(1, username);
            getCredentialsStmt.setString(2, String.valueOf(password));
            getCredentialsStmt.setString(3, designation);
            
            ResultSet getCredentialsRs = getCredentialsStmt.executeQuery();
            if(getCredentialsRs.next()) {
                return true;
            }
            
            else {
                return false;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return false;
    }
    
    public int getEmployeeId(Login login) {
        try {
            String username = login.getUsername();
            char[] password = login.getPassword();
            String designation = login.getDesignation();
            
            String getEmployeeIdQuery = "select e.employee_id from employee e inner join employee_designation ed"
                    + " on e.employee_id = ed.employee_id inner join designation d on ed.designation_id = d.designation_id"
                    + " where email = ? and password = ? and title = ?";
            
            PreparedStatement getEmployeeIdStmt = conn.prepareStatement(getEmployeeIdQuery);
            getEmployeeIdStmt.setString(1, username);
            getEmployeeIdStmt.setString(2, String.valueOf(password));
            getEmployeeIdStmt.setString(3, designation);
            
            ResultSet getEmployeeIdRs = getEmployeeIdStmt.executeQuery();
            if(getEmployeeIdRs.next())
                return getEmployeeIdRs.getInt(1);
            else
                return -1;
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return -1;
    }
}
