package com.pm.library.dao.implementations;

import com.pm.library.dao.interfaces.BookInterface;
import com.pm.library.dao.interfaces.BorrowedBookInterface;
import com.pm.library.model.Book;
import com.pm.library.model.BorrowedBook;
import com.pm.library.model.Member;
import com.pm.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BorrowedBookImplementation implements BorrowedBookInterface {
    static Connection con = DatabaseConnection.getConnection();

    // CRUD OPERATIONS
    @Override
    public  boolean add(int memberId, int bookId, int borrowingDays) {
        String sql = "INSERT INTO borrowedbooks (memberid, bookid, borrowDate, returnDate) VALUES (?, ?, CURDATE(), ?)";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, memberId);
            ps.setInt(2, bookId);

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

    @Override
    public boolean update(BorrowedBook borrowedBookbook) {
        return false;
    }

    @Override
    public  boolean delete(int id) {
        String sql = "DELETE FROM borrowedbooks WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }



    @Override
    public  BorrowedBook getBorrowedBook(int id) throws SQLException {
        String sql = "SELECT * FROM borrowedbooks WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int memberId = resultSet.getInt("memberId");
                int bookId = resultSet.getInt("bookId");
                Date borrowDate = resultSet.getDate("borrowDate");
                Date returnDate = resultSet.getDate("returnDate");

                return new BorrowedBook(id, memberId, bookId, borrowDate, returnDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the borrowed book: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }
    public  BorrowedBook getBorrowedBookByMember(Member member) throws SQLException {
        String sql = "SELECT * FROM members m, borrowedbooks bb WHERE bb.memberId = m.id and m.id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, member.getId());
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("bb.id");
                int memberId = resultSet.getInt("memberId");
                int bookId = resultSet.getInt("bookId");
                Date borrowDate = resultSet.getDate("borrowDate");
                Date returnDate = resultSet.getDate("returnDate");

                return new BorrowedBook(id, memberId, bookId, borrowDate, returnDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the borrowed book: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public  List<BorrowedBook> getBorrowedBooks() throws SQLException {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        String sql = "SELECT * FROM borrowedBooks";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int memberId = resultSet.getInt("memberId");
                int bookId = resultSet.getInt("bookId");
                Date borrowDate = resultSet.getDate("borrowDate");
                Date returnDate = resultSet.getDate("returnDate");

                BorrowedBook borrowedBook = new BorrowedBook(id, memberId, bookId, borrowDate, returnDate);
                borrowedBooks.add(borrowedBook);
            }
        } catch (SQLException e) {
            System.err.println("Error getting borrowed books: " + e.getMessage());
            // You can throw or handle the exception as needed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // You can throw or handle the exception as needed
        }

        return borrowedBooks;
    }

    // METHOD HELPERS
    // HELPER METHODS
}
