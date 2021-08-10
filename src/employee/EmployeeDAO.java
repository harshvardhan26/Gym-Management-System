/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import common.DBConnect;

/**
 *
 * @author Harshvardhan Kale
 */
public class EmployeeDAO {
    
    Connection conn;
    static final String EMPLOYEE_INSERT_QUERY = "insert into employee (gym_id, first_name, last_name, phone, email, gender, dob)"
            + " values (?, ?, ?, ?, ?, ?, ?)";
    static final String GET_GYM_ID_QUERY = "select gym_id from gym where branch_name = ? ";
    static final String GET_DESIGNATION_ID_QUERY = "select designation_id from designation where title = ?";
    static final String EMPLOYEE_DESIGNATION_INSERT_QUERY = "insert into employee_designation (designation_id, doj, password) values (?, ?, ?)";
    static final String EMPLOYEE_ADDRESS_INSERT_QUERY = "insert into employee_address (house_number, street, city, zipcode) values (?, ?, ?, ?)";
    static final String LIST_EMPLOYEES_QUERY = "select first_name, last_name, email, dob, phone, gender, title, branch_name, doj, house_number, street, city, zipcode"
                    + " from employee e inner join employee_designation ed on e.employee_id = ed.employee_id"
                    + " inner join designation d on ed.designation_id = d.designation_id"
                    + " inner join employee_address ea on e.employee_id = ea.employee_id"
                    + " inner join gym g on e.gym_id =  g.gym_id";
    static final String FIND_EMPLOYEE_QUERY = "select e.employee_id, e.gym_id, email, phone, branch_name, house_number, street, city, zipcode "
                    + " from employee e inner join employee_address ea on e.employee_id = ea.employee_id"
                    + " inner join gym g on e.gym_id = g.gym_id"
                    + " where first_name = ? and email = ?";
    static final String UPDATE_EMPLOYEE_QUERY = "update employee set gym_id = ?, email = ?, phone = ? where employee_id = ?";
    static final String UPDATE_EMPLOYEE_ADDRESS_QUERY = "update employee_address set house_number = ?, street = ?, city = ?, zipcode = ?"
                    + " where employee_id = ?";
    static final String DELETE_EMPLOYEE_QUERY = "delete from employee where employee_id = ?";
    static final String ADD_DIET_QUERY = "insert into diet values (?, ?, ?, ?)";
    static final String LIST_DIETS_QUERY = "select m.first_name, m.email, diet_description, duration"
                    + " from member m inner join diet d on m.member_id = d.member_id"
                    + " inner join employee e on d.employee_id = e.employee_id"
                    + " where e.employee_id = ?";
    static final String ADD_WORKOUT_QUERY = "insert into workout values (?, ?, ?, ?)";
    static final String LIST_WORKOUTS_QUERY = "select m.first_name, m.email, workout_description, duration"
                    + " from member m inner join workout w on m.member_id = w.member_id"
                    + " inner join employee e on w.employee_id = e.employee_id"
                    + " where e.employee_id = ?";
    
    public EmployeeDAO() {
        this.conn = DBConnect.getConnection();
    }
    
    public void addEmployee(Employee employee) {
        try {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            String phone = employee.getPhone();
            String email = employee.getEmail();
            String gender = employee.getGender();
            java.util.Date dob = employee.getDob();
            java.util.Date doj = employee.getDoj();
            String designation = employee.getDesignation();
            String branchName = employee.getBranchName();
            char[] password = employee.getPassword();
            String houseNumber = employee.getHouseNumber();
            String street = employee.getStreet();
            String city = employee.getCity();
            String zipcode = employee.getZipcode();
            
            int gymId= getGymId(branchName);
            int designationId = getDesignationId(designation);
            
            PreparedStatement employeeInsertStmt = conn.prepareStatement(EMPLOYEE_INSERT_QUERY);
            employeeInsertStmt.setInt(1, gymId);
            employeeInsertStmt.setString(2, firstName);
            employeeInsertStmt.setString(3, lastName);
            employeeInsertStmt.setString(4, phone);
            employeeInsertStmt.setString(5, email);
            employeeInsertStmt.setString(6, gender);
            employeeInsertStmt.setDate(7, new java.sql.Date(dob.getTime()));
            employeeInsertStmt.executeUpdate();
            employeeInsertStmt.close();
            
            addDesignation(designationId, doj, password);
            addAddress(houseNumber, street, city, zipcode);
            
            JOptionPane.showMessageDialog(null, "Employee Added");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    private int getGymId(String branchName) {
        try {
            PreparedStatement getGymIdStmt = conn.prepareStatement(GET_GYM_ID_QUERY);
            getGymIdStmt.setString(1, branchName);
            ResultSet getGymIdRs = getGymIdStmt.executeQuery();
            
            getGymIdRs.next();
            int gymID = getGymIdRs.getInt(1);
            
            getGymIdRs.close();
            getGymIdStmt.close();
            
            return gymID;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return -1;
    }
    
    private int getDesignationId(String designation) {
        try {
            PreparedStatement getDesignationIdStmt = conn.prepareStatement(GET_DESIGNATION_ID_QUERY);
            getDesignationIdStmt.setString(1, designation);
            ResultSet getDesignationIdRs = getDesignationIdStmt.executeQuery();
            
            getDesignationIdRs.next();
            int designationId = getDesignationIdRs.getInt(1);
            
            getDesignationIdRs.close();
            getDesignationIdStmt.close();
            
            return designationId;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return -1;
    }
    
    private void addDesignation(int designationId, java.util.Date doj, char[] password) {
      try {
            PreparedStatement employeeDesignationInsertStmt = conn.prepareStatement(EMPLOYEE_DESIGNATION_INSERT_QUERY);
            employeeDesignationInsertStmt.setInt(1, designationId);
            employeeDesignationInsertStmt.setDate(2, new java.sql.Date(doj.getTime()));
            employeeDesignationInsertStmt.setString(3, String.valueOf(password));
            employeeDesignationInsertStmt.executeUpdate();
            
            employeeDesignationInsertStmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void addAddress(String  houseNumber, String street, String city, String zipcode) {
     try {
            PreparedStatement employeeAddressInsertStmt = conn.prepareStatement(EMPLOYEE_ADDRESS_INSERT_QUERY);
            employeeAddressInsertStmt.setString(1, houseNumber);
            employeeAddressInsertStmt.setString(2, street);
            employeeAddressInsertStmt.setString(3, city);
            employeeAddressInsertStmt.setString(4, zipcode);
            employeeAddressInsertStmt.executeUpdate();
            
            employeeAddressInsertStmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void listEmployees(DefaultTableModel employeeListModel) {
        try {
            Statement listEmployeesStmt = conn.createStatement();
            ResultSet listEmployeesRs = listEmployeesStmt.executeQuery(LIST_EMPLOYEES_QUERY);
            
            String firstName = "", lastName = "", email = "", dob = "", phone = "", gender = "", title = "", branchName = "", doj = "", houseNumber = "",
                    street = "", city = "", zipcode = "";
            boolean empty = true;
            
            while(listEmployeesRs.next()) {
                firstName = listEmployeesRs.getString(1);
                lastName = listEmployeesRs.getString(2);
                email = listEmployeesRs.getString(3);
                dob = String.valueOf(listEmployeesRs.getDate(4));
                phone = listEmployeesRs.getString(5);
                gender = listEmployeesRs.getString(6);
                title = listEmployeesRs.getString(7);
                branchName = listEmployeesRs.getString(8);
                doj = String.valueOf(listEmployeesRs.getDate(9));
                houseNumber = listEmployeesRs.getString(10);
                street = listEmployeesRs.getString(11);
                city = listEmployeesRs.getString(12);
                zipcode = listEmployeesRs.getString(13);

                String[] rowArr = {firstName, lastName, email, dob, phone, gender, title, branchName, doj, houseNumber, street, city, zipcode};
                employeeListModel.addRow(rowArr);
                empty = false;
            }
            
            if(empty)
                JOptionPane.showMessageDialog(null, "No Employees Found");
            
            listEmployeesRs.close();
            listEmployeesStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public List<String> findEmployee(String firstName, String email) {
        List<String> fetchedValues = new ArrayList<String>();
        try {
            PreparedStatement findEmployeeStmt = conn.prepareStatement(FIND_EMPLOYEE_QUERY);
            findEmployeeStmt.setString(1, firstName);
            findEmployeeStmt.setString(2, email);
            
            ResultSet findEmployeeRs = findEmployeeStmt.executeQuery();
            
            if(findEmployeeRs.next()) {
                fetchedValues.add(String.valueOf(findEmployeeRs.getInt(1)));
                fetchedValues.add(String.valueOf(findEmployeeRs.getInt(2)));
                fetchedValues.add(findEmployeeRs.getString(3));
                fetchedValues.add(findEmployeeRs.getString(4));
                fetchedValues.add(findEmployeeRs.getString(5));
                fetchedValues.add(findEmployeeRs.getString(6));
                fetchedValues.add(findEmployeeRs.getString(7));
                fetchedValues.add(findEmployeeRs.getString(8));
                fetchedValues.add(findEmployeeRs.getString(9));
            }
            
            else
                fetchedValues = null;
            
            findEmployeeRs.close();
            findEmployeeStmt.close();
            conn.close();
            return fetchedValues;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return null;
    }
    
    public void updateEmployee(int employeeId, String email, String phone, String branchName, 
            String houseNumber, String street, String city, String zipcode) {
        try {
            int gymId = getGymId(branchName);
            PreparedStatement updateEmployeeStmt = conn.prepareStatement(UPDATE_EMPLOYEE_QUERY);
            updateEmployeeStmt.setInt(1, gymId);
            updateEmployeeStmt.setString(2, email);
            updateEmployeeStmt.setString(3, phone);
            updateEmployeeStmt.setInt(4, employeeId);
            updateEmployeeStmt.executeUpdate();
            
            PreparedStatement updateEmployeeAddressStmt = conn.prepareStatement(UPDATE_EMPLOYEE_ADDRESS_QUERY);
            updateEmployeeAddressStmt.setString(1, houseNumber);
            updateEmployeeAddressStmt.setString(2, street);
            updateEmployeeAddressStmt.setString(3, city);
            updateEmployeeAddressStmt.setString(4, zipcode);
            updateEmployeeAddressStmt.setInt(5, employeeId);
            updateEmployeeAddressStmt.executeUpdate();
            
            updateEmployeeAddressStmt.close();
            updateEmployeeStmt.close();
            
            JOptionPane.showMessageDialog(null, "Employee Updated");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void deleteEmployee(int employeeId) {
        try {
            PreparedStatement deleteEmployeeStmt = conn.prepareStatement(DELETE_EMPLOYEE_QUERY);
            deleteEmployeeStmt.setInt(1, employeeId);
            deleteEmployeeStmt.executeUpdate();
            
            deleteEmployeeStmt.close();
            JOptionPane.showMessageDialog(null, "Employee Deleted");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void addDiet(int employeeId, int memberId, String diet, int duration) {
        try {
            PreparedStatement addDietStmt = conn.prepareStatement(ADD_DIET_QUERY);
            addDietStmt.setInt(1, employeeId);
            addDietStmt.setInt(2, memberId);
            addDietStmt.setString(3, diet);
            addDietStmt.setInt(4, duration);
            addDietStmt.executeUpdate();
            
            addDietStmt.close();
            JOptionPane.showMessageDialog(null, "Diet Added");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void listDiets(DefaultTableModel dietListModel, int employeeId) {
        try {
            PreparedStatement listDietsStmt = conn.prepareStatement(LIST_DIETS_QUERY);
            listDietsStmt.setInt(1, employeeId);
            
            ResultSet listDietsRs = listDietsStmt.executeQuery();
            
            String firstName = "", email = "", diet = "", duration = "";
            boolean empty = true;
            
            while(listDietsRs.next()) {
                firstName = listDietsRs.getString(1);
                email = listDietsRs.getString(2);
                diet = listDietsRs.getString(3);
                duration = String.valueOf(listDietsRs.getInt(4));

                String[] rowArr = {firstName, email, diet, duration};
                dietListModel.addRow(rowArr);
                empty = false;
            }
            
            if(empty)
                JOptionPane.showMessageDialog(null, "No Diet Records Found");
            
            listDietsRs.close();
            listDietsStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void addWorkout(int employeeId, int memberId, String workout, int duration) {
        try {
            PreparedStatement addWorkoutStmt = conn.prepareStatement(ADD_WORKOUT_QUERY);
            addWorkoutStmt.setInt(1, employeeId);
            addWorkoutStmt.setInt(2, memberId);
            addWorkoutStmt.setString(3, workout);
            addWorkoutStmt.setInt(4, duration);
            addWorkoutStmt.executeUpdate();
            
            addWorkoutStmt.close();
            JOptionPane.showMessageDialog(null, "Workout Added");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void listWorkouts(DefaultTableModel workoutListModel, int employeeId) {
        try {
            PreparedStatement listWorkoutsStmt = conn.prepareStatement(LIST_WORKOUTS_QUERY);
            listWorkoutsStmt.setInt(1, employeeId);
            
            ResultSet listWorkoutsRs = listWorkoutsStmt.executeQuery();
            
            String firstName = "", email = "", workout = "", duration = "";
            boolean empty = true;
            
            while(listWorkoutsRs.next()) {
                firstName = listWorkoutsRs.getString(1);
                email = listWorkoutsRs.getString(2);
                workout = listWorkoutsRs.getString(3);
                duration = String.valueOf(listWorkoutsRs.getInt(4));

                String[] rowArr = {firstName, email, workout, duration};
                workoutListModel.addRow(rowArr);
                empty = false;
            }
            
            if(empty)
                JOptionPane.showMessageDialog(null, "No Workout Records Found");
            
            listWorkoutsRs.close();
            listWorkoutsStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}

