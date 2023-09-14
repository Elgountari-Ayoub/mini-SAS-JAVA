package com.pm.library.dao.implementations;

import com.pm.library.dao.interfaces.BookReferenceInterface;
import com.pm.library.model.BookReference;
import com.pm.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookReferenceImplementation implements BookReferenceInterface {
    static Connection con = DatabaseConnection.getConnection();

    // CRUD OPERATIONS
    @Override
    public boolean add(BookReference bookReference) {
        // Check if the book reference already exist ?
        String sql = "INSERT INTO booksReference (isbn, title, author, quantity) VALUES (?, ?, ?, ?) ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bookReference.getIsbn());
            ps.setString(2, bookReference.getTitle());
            ps.setString(3, bookReference.getAuthor());
            ps.setInt(4, bookReference.getQuantity());
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
    public boolean update(BookReference bookReference) {
        String sql = "UPDATE booksReference SET title=?, author=?, quantity=? WHERE isbn=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bookReference.getTitle());
            ps.setString(2, bookReference.getAuthor());
            ps.setInt(3, bookReference.getQuantity());
            ps.setString(4, bookReference.getIsbn());

            int rowsUpdated = ps.executeUpdate();

            // Check if any rows were updated.
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating book reference: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String isbn) {
        String sql = "DELETE FROM booksReference WHERE isbn=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, isbn);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book reference: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    public BookReference getBookReference(String isbn) throws SQLException {
        String sql = "SELECT * FROM booksReference WHERE isbn = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, isbn);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("quantity");

                return new BookReference(isbn, title, author, quantity);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book reference " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }
    public BookReference getBookReferenceByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM booksReference WHERE title = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("quantity");

                return new BookReference(isbn, title, author, quantity);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book reference " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }
    public BookReference getBookReferenceByAuthor(String author) throws SQLException {
        String sql = "SELECT * FROM booksReference WHERE author = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, author);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                int quantity = resultSet.getInt("quantity");

                return new BookReference(isbn, title, author, quantity);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book reference " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<BookReference> getBooksReference() throws SQLException {
        List<BookReference> bookReferences = new ArrayList<>();
        String sql = "SELECT * FROM booksReference";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("quantity");

                BookReference bookReference = new BookReference(isbn, title, author, quantity);
                bookReferences.add(bookReference);
            }
        } catch (SQLException e) {
            System.err.println("Error getting book references: " + e.getMessage());
            // You can throw or handle the exception as needed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // You can throw or handle the exception as needed
        } finally {
            // Close resources like PreparedStatement and ResultSet here if necessary
        }

        return bookReferences;
    }

    // METHOD HELPERS
    public static boolean isBookReferenceExist(String isbn) {
        String sql = "SELECT COUNT(*) as 'count' FROM booksReference WHERE isbn = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, isbn);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting the book reference count => " + e.getMessage());
        }
        return false;
    }

    public List<BookReference> getAvailableBooks() throws SQLException {
        List<BookReference> bookReferences = this.getBooksReference();

        return bookReferences.stream()
                .filter(bookReference -> bookReference.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    public static boolean updateBookStatus() {
        try {
            String sql = "UPDATE books AS b JOIN borrowedbooks AS bb ON b.id = bb.bookid SET b.status = 'lost' WHERE bb.returnDate < CURDATE() AND b.status = 'borrowed';";

            PreparedStatement ps = con.prepareStatement(sql);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<BookReference> getBorrowedBookReferences() throws SQLException {
        List<BookReference> bookReferences = new ArrayList<>();
        String sql = "SELECT br.isbn, br.title, br.author, COUNT(*) AS borrowed_count FROM booksreference AS br JOIN books AS b ON br.isbn = b.isbn WHERE b.status = 'borrowed' GROUP BY br.isbn, br.title, br.author;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("borrowed_count");

                BookReference bookReference = new BookReference(isbn, title, author, quantity);
                bookReferences.add(bookReference);
            }
        } catch (SQLException e) {
            System.err.println("Error getting borrowed book references: " + e.getMessage());
            // You can throw or handle the exception as needed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // You can throw or handle the exception as needed
        } finally {
            // Close resources like PreparedStatement and ResultSet here if necessary
        }

        return bookReferences;
    }

    public List<BookReference> getLostBookReferences() throws SQLException {
        List<BookReference> bookReferences = new ArrayList<>();
        String sql = "SELECT br.isbn, br.title, br.author, COUNT(*) AS borrowed_count FROM booksreference AS br JOIN books AS b ON br.isbn = b.isbn WHERE b.status = 'lost' GROUP BY br.isbn, br.title, br.author;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int quantity = resultSet.getInt("borrowed_count");

                BookReference bookReference = new BookReference(isbn, title, author, quantity);
                bookReferences.add(bookReference);
            }
        } catch (SQLException e) {
            System.err.println("Error getting borrowed book references: " + e.getMessage());
            // You can throw or handle the exception as needed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // You can throw or handle the exception as needed
        } finally {
            // Close resources like PreparedStatement and ResultSet here if necessary
        }

        return bookReferences;
    }



}
