package com.pm.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book {

    private int id;
    private String isbn;
    private String status = "available";

    public Book() {}

    public Book(Book book) {
        this.id = book.id;
        this.isbn = book.isbn;
        this.status = book.status;
    }

    public Book(int id, String isbn) {
        this.id = id;
        this.isbn = isbn;
        this.status = "available";
    }

    public Book(int id, String isbn, String status) {
        this.id = id;
        this.isbn = isbn;
        this.status = status;
    }

    // GETTERS && SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "ISBN: " + this.isbn +
                "\t Title: " + this.id +
                "\t Author: " + this.isbn +
                "\t Quantity: " + this.status;
    }

}
