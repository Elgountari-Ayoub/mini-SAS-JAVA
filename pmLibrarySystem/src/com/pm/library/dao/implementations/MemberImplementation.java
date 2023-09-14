package com.pm.library.dao.implementations;

import com.pm.library.dao.interfaces.MemberInterface;
import com.pm.library.model.Book;
import com.pm.library.model.Member;
import com.pm.library.util.DatabaseConnection;

import java.sql.Connection;
import java.util.ArrayList;
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
        List<Member> members = new ArrayList<>();
        String sql = "Select * from members";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone");
                String address = resultSet.getString("address");

                Member member = new Member(id, name, phoneNumber, address);
                members.add(member);
            }
            return members;
        } catch (SQLException e) {
            System.err.println("Error getting books: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Member getMemberByPhone(String phoneNumber) throws SQLException {
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


    public boolean hasBook(Member member) {
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
