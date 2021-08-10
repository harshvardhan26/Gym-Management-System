/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import common.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Harshvardhan Kale
 */
public class MemberDAO {
    
    Connection conn;
    static final String MEMBER_INSERT_QUERY = "insert into member (gym_id, first_name, last_name, phone, email, gender, dob) values (?, ?, ?, ?, ?, ?, ?)";
    static final String GET_GYM_ID_QUERY = "select gym_id from gym where branch_name = ? ";
    static final String GET_MEMBERSHIP_ID_QUERY = "select membership_id from membership where duration = ?";
    static final String MEMBER_MEMBERSHIP_INSERT_QUERY = "insert into member_membership (membership_id, start_date, end_date, advance_paid) values (?, ?, ?, ?)";
    static final String MEMBER_TRAITS_INSERT_QUERY = "insert into member_traits (height, weight, body_fat, injury) values (?, ?, ?, ?)";
    static final String LIST_MEMBERS_QUERY = "select first_name, last_name, email, dob, phone, gender, height, weight, body_fat, injury, branch_name, duration, price, start_date, end_date, advance_paid"
                    + " from member m inner join member_traits mt on m.member_id = mt.member_id"
                    + " inner join member_membership mm on m.member_id = mm.member_id"
                    + " inner join membership ms on mm.membership_id = ms.membership_id"
                    + " inner join gym g on m.gym_id = g.gym_id";
    static final String FIND_MEMBER_QUERY = "select m.member_id, m.gym_id, email, phone, branch_name, weight, body_fat, injury, advance_paid, height"
                    + " from member m inner join member_traits mt on m.member_id = mt.member_id"
                    + " inner join gym g on m.gym_id = g.gym_id"
                    + " inner join member_membership mm on m.member_id = mm.member_id"
                    + " inner join membership ms on mm.membership_id = ms.membership_id"
                    + " where first_name = ? and email = ?";
    static final String UPDATE_MEMBER_QUERY = "update member set gym_id = ?, email = ?, phone = ? where member_id = ?";
    static final String UPDATE_MEMBER_TRAITS_QUERY = "update member_traits set weight = ?, body_fat = ?, injury = ? where member_id = ?";
    static final String UPDATE_MEMBER_MEMBERSHIP_QUERY = "update member_membership set advance_paid = ? where member_id = ?";
    static final String DELETE_MEMBER_QUERY = "delete from member where member_id = ?";
    
    public MemberDAO() {
        this.conn = DBConnect.getConnection();
    }
    
    public void addMember(Member member) {
        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String phone = member.getPhone();
        String email = member.getEmail();
        String gender = member.getGender();
        java.util.Date dob = member.getDob();
        int height = member.getHeight();
        double weight = member.getWeight();
        double bodyFat = member.getBodyFat();
        String injury = member.getInjury();
        int duration = member.getDuration();
        java.util.Date startDate = member.getStartDate();
        String branchName = member.getBranchName();
        int advancePaid = member.getAdvancePaid();
        
        int gymId = getGymId(branchName);
        int membershipId = getMembershipId(duration);
        
       java.util.Date endDate = getEndDate(startDate, duration);
        
       try {
            PreparedStatement memberInsertStmt = conn.prepareStatement(MEMBER_INSERT_QUERY);
            memberInsertStmt.setInt(1, gymId);
            memberInsertStmt.setString(2, firstName);
            memberInsertStmt.setString(3, lastName);
            memberInsertStmt.setString(4, phone);
            memberInsertStmt.setString(5, email);
            memberInsertStmt.setString(6, gender);
            memberInsertStmt.setDate(7, new java.sql.Date(dob.getTime()));
            memberInsertStmt.executeUpdate();
            memberInsertStmt.close();
            
            addMembership(membershipId, startDate, endDate, advancePaid);
            addMemberTraits(height, weight, bodyFat, injury);
            
            JOptionPane.showMessageDialog(null, "Member Added");
            conn.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
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
    
    private int getMembershipId(int duration) {
        try {
            PreparedStatement getMembershipIdStmt = conn.prepareStatement(GET_MEMBERSHIP_ID_QUERY);
            getMembershipIdStmt.setInt(1, duration);
            ResultSet getMembershipIdRs = getMembershipIdStmt.executeQuery();
            
            getMembershipIdRs.next();
            int membershipID = getMembershipIdRs.getInt(1);
            
            getMembershipIdRs.close();
            getMembershipIdStmt.close();
            
            return membershipID;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return -1;
    }
    
    private void addMembership(int membershipId, java.util.Date startDate, java.util.Date endDate, int advancePaid) {
        try {
            PreparedStatement memberMembershipInsertStmt = conn.prepareStatement(MEMBER_MEMBERSHIP_INSERT_QUERY);
            memberMembershipInsertStmt.setInt(1, membershipId);
            memberMembershipInsertStmt.setDate(2, new java.sql.Date(startDate.getTime()));
            memberMembershipInsertStmt.setDate(3, new java.sql.Date(endDate.getTime()));
            memberMembershipInsertStmt.setInt(4, advancePaid);
            memberMembershipInsertStmt.executeUpdate();
            
            memberMembershipInsertStmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private java.util.Date getEndDate(java.util.Date startDate, int duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, duration * 30);
        return calendar.getTime();
    }
    
    private void addMemberTraits(int height, double weight, double bodyFat, String injury) {
        try {
            PreparedStatement memberTraitsInsertStmt = conn.prepareStatement(MEMBER_TRAITS_INSERT_QUERY);
            memberTraitsInsertStmt.setInt(1, height);
            memberTraitsInsertStmt.setDouble(2, weight);
            memberTraitsInsertStmt.setDouble(3, bodyFat);
            memberTraitsInsertStmt.setString(4, injury);
            memberTraitsInsertStmt.executeUpdate();
            
            memberTraitsInsertStmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void listMembers(DefaultTableModel memberListModel) {
        try {
            Statement listMembersStmt = conn.createStatement();
            ResultSet listMembersRs = listMembersStmt.executeQuery(LIST_MEMBERS_QUERY);
            
            String firstName = "", lastName = "", email = "", dob = "", phone = "", gender = "", height = "", weight = "", bodyFat = "", injury = "", branchName = "",
                    duration = "", price = "", startDate = "", endDate = "", advancePaid = "";
            boolean empty = true;
            
            while(listMembersRs.next()) {
                firstName = listMembersRs.getString(1);
                lastName = listMembersRs.getString(2);
                email = listMembersRs.getString(3);
                dob = String.valueOf(listMembersRs.getDate(4));
                phone = listMembersRs.getString(5);
                gender = listMembersRs.getString(6);
                height = String.valueOf(listMembersRs.getInt(7));
                weight = String.valueOf(listMembersRs.getDouble(8));
                bodyFat = String.valueOf(listMembersRs.getDouble(9));
                injury = listMembersRs.getString(10);
                branchName = listMembersRs.getString(11);
                duration = String.valueOf(listMembersRs.getInt(12));
                price = String.valueOf(listMembersRs.getInt(13));
                startDate = String.valueOf(listMembersRs.getDate(14));
                endDate = String.valueOf(listMembersRs.getDate(15));
                advancePaid = String.valueOf(listMembersRs.getInt(16));
                
                String[] rowArr = {firstName, lastName, email, dob, phone, gender, height, weight, bodyFat, injury, branchName, duration, price, startDate, endDate, advancePaid};
                memberListModel.addRow(rowArr);
                empty = false;
            }
            
            if(empty)
                JOptionPane.showMessageDialog(null, "No Members Found");
            
            listMembersRs.close();
            listMembersStmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public List<String> findMember(String firstName, String email) {
        try {
            List<String> fetchedValues = new ArrayList<>();
            PreparedStatement findMemberStmt = conn.prepareStatement(FIND_MEMBER_QUERY);
            findMemberStmt.setString(1, firstName);
            findMemberStmt.setString(2, email);
            
            ResultSet findMemberRs = findMemberStmt.executeQuery();
            
            if(findMemberRs.next()) {
                fetchedValues.add(String.valueOf(findMemberRs.getInt(1)));
                fetchedValues.add(String.valueOf(findMemberRs.getInt(2)));
                fetchedValues.add(findMemberRs.getString(3));
                fetchedValues.add(findMemberRs.getString(4));
                fetchedValues.add(findMemberRs.getString(5));
                fetchedValues.add(String.valueOf(findMemberRs.getDouble(6)));
                fetchedValues.add(String.valueOf(findMemberRs.getDouble(7)));
                fetchedValues.add(findMemberRs.getString(8));
                fetchedValues.add(String.valueOf(findMemberRs.getInt(9)));
                fetchedValues.add(String.valueOf(findMemberRs.getInt(10)));
            }
            
            else
                fetchedValues = null;
            
            findMemberRs.close();
            findMemberStmt.close();
            conn.close();
            return fetchedValues;
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
                    return null;
    }
    
    public void updateMember(int memberId, String email, String phone, String branchName, 
            double weight, double bodyFat, String injury, int advancePaid) {
        try {
            int gymId = getGymId(branchName);
            PreparedStatement updateMemberStmt = conn.prepareStatement(UPDATE_MEMBER_QUERY);
            updateMemberStmt.setInt(1, gymId);
            updateMemberStmt.setString(2, email);
            updateMemberStmt.setString(3, phone);
            updateMemberStmt.setInt(4, memberId);
            updateMemberStmt.executeUpdate();
            
            PreparedStatement updateMemberTraitsStmt = conn.prepareStatement(UPDATE_MEMBER_TRAITS_QUERY);
            updateMemberTraitsStmt.setDouble(1, weight);
            updateMemberTraitsStmt.setDouble(2, bodyFat);
            updateMemberTraitsStmt.setString(3, injury);
            updateMemberTraitsStmt.setInt(4, memberId);
            updateMemberTraitsStmt.executeUpdate();
            
            PreparedStatement updateMemberMembershipStmt = conn.prepareStatement(UPDATE_MEMBER_MEMBERSHIP_QUERY);
            updateMemberMembershipStmt.setInt(1, advancePaid);
            updateMemberMembershipStmt.setInt(2, memberId);
            updateMemberMembershipStmt.executeUpdate();
            
            updateMemberMembershipStmt.close();
            updateMemberTraitsStmt.close();
            updateMemberStmt.close();
            
            JOptionPane.showMessageDialog(null, "Member Updated");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void deleteMember(int memberId) {
        try {
            PreparedStatement deleteMemberStmt = conn.prepareStatement(DELETE_MEMBER_QUERY);
            deleteMemberStmt.setInt(1, memberId);
            deleteMemberStmt.executeUpdate();
            
            deleteMemberStmt.close();
            JOptionPane.showMessageDialog(null, "Member Deleted");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
        
}