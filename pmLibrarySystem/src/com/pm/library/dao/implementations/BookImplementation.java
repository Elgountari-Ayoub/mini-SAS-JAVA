package com.pm.library.dao.implementations;

import com.pm.library.dao.interfaces.BookInterface;
import com.pm.library.dao.interfaces.BookReferenceInterface;
import com.pm.library.model.Book;
import com.pm.library.model.BookReference;
import com.pm.library.model.Member;
import com.pm.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookImplementation implements BookInterface {
    static Connection con = DatabaseConnection.getConnection();

    public static Book findAvailableBookByISBN(String isbn) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM books WHERE isbn = ? AND status = 'available' LIMIT 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, isbn);

            // Execute the query
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String bookIsbn = resultSet.getString("isbn");
                String bookStatus = resultSet.getString("status");

                return new Book(bookId, bookIsbn, bookStatus);
            } else {
                // No available book found with the given ISBN
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    // CRUD OPERATIONS
    @Override
    public boolean add(Book book) {
        // Check if the book reference already exist ?
        String sql = "INSERT INTO books (isbn, status) VALUES (?, ?) ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting book reference: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE booksReference SET status=?  WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, book.getId());
            ps.setString(1, book.getStatus());

            int rowsUpdated = ps.executeUpdate();

            // Check if any rows were updated.
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM books WHERE id=?";
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
    public Book getBook(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String status = resultSet.getString("status");

                return new Book(id, isbn, status);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String status = resultSet.getString("status");

                Book book = new Book(id, isbn, status);
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error getting books: " + e.getMessage());
            // You can throw or handle the exception as needed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // You can throw or handle the exception as needed
        } finally {
            // Close resources like PreparedStatement and ResultSet here if necessary
        }

        return books;
    }

    // METHOD HELPERS
    public static boolean isBookExist(int id) {
        String sql = "SELECT COUNT(*) as 'count' FROM books WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book count: " + e.getMessage());
        }
        return false;
    }

    // HELPER METHODS
    public static BookReference getBookByTitleAndAuthor(String title, String author) {
        String sql = "SELECT * FROM booksReference WHERE title = ? AND author = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                int quantity = resultSet.getInt("quantity");
                return new BookReference(isbn, title, author, quantity);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting book by title and author: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean borrow(Member member) {
        try {
//            check if the member has no books for the moment

//            check if the book not borrowed for the moment

//            affect the book to the member in borrowedbooks table

            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean isBookBorrowed(Book book) {
        try {
            return Objects.equals((getBook(book.getId())).getStatus(), "borrowed");
        } catch (Exception e) {
            return false;
        }
    }
}
