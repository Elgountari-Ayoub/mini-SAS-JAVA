package com.pm.library.dao.implementations;

import com.pm.library.dao.interfaces.MemberInterface;
import com.pm.library.model.Book;
import com.pm.library.model.Member;
import com.pm.library.util.DatabaseConnection;

import java.sql.Connection;
import java.util.Date;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class MemberImplementation implements MemberInterface {
    static Connection con = DatabaseConnection.getConnection();

    // CRUD OPERATIONS
    @Override
    public boolean add(Member member) {
        // Check if the member already exist ?
        String sql = "INSERT INTO members(name, phone, address) values (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, member.getName());
            ps.setString(2, member.getPhoneNumber());
            ps.setString(3, member.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving member reference: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Member member) {
        String sql = "UPDATE members SET name=?, phone=?, address=?  WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, member.getName());
            ps.setString(2, member.getPhoneNumber());
            ps.setString(3, member.getAddress());
            ps.setInt(4, member.getId());

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating member: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM members WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting this member: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Member getMember(int id) throws SQLException {
        String sql = "SELECT * FROM members WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone");
                String address = resultSet.getString("address");

                return new Member(id, name, phoneNumber, address);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the member: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Member> getMembers() throws SQLException {
        return null;
    }

    // METHOD HELPERS
    public static boolean isMemberExist(int id) {
        String sql = "SELECT COUNT(*) as 'count' FROM members WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the member count: " + e.getMessage());
        }
        return false;
    }

    public  Member getMemberByPhone(String phoneNumber) throws SQLException {
        String sql = "SELECT * FROM members WHERE phone = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                System.out.println();
                Member member = new Member(id, name, phoneNumber, address);
                return member;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the member: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    public static boolean borrow(Member member, Book book, int borrowingDays) {
        String sql = "INSERT INTO borrowedbooks (memberid, bookid, borrowDate, returnDate) VALUES (?, ?, CURDATE(), ?)";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, member.getId());
            ps.setInt(2, book.getId());

            Date currentDate;
            currentDate = new Date();
            long returnDateMillis = currentDate.getTime() + ((long) borrowingDays * 24 * 60 * 60 * 1000);
            java.sql.Date returnDate = new java.sql.Date(returnDateMillis);
            ps.setDate(3, returnDate);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error borrowing the book: " + e.getMessage());
            return false;
        }
    }

    public  boolean hasBook(Member member) {
        String sql = "SELECT count(*) as 'count' from borrowedbooks bb WHERE bb.memberId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, member.getId());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (
                SQLException e) {
            System.err.println("Error getting the member count: " + e.getMessage());
        } catch (
                Exception e) {
            return false;
        }
        return false;
    }

}
