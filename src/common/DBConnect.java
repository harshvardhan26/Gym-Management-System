/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Harshvardhan Kale
 */
public class DBConnect {
    
    public static Connection getConnection() {
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","harshvardhan","harsh");
            return conn;
        }
        
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return null;
    }
    
}
