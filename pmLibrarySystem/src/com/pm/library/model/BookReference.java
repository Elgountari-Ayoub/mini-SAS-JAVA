package com.pm.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookReference {
    private String isbn;
    private String title;
    private String author;
    private int quantity = 1;

    // CONSTRUCTORS
    public BookReference() {
    }

    public BookReference(String isbn, String title, String author, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public BookReference(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public BookReference(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public BookReference(BookReference bookReference) {
        this.isbn = bookReference.isbn;
        this.title = bookReference.title;
        this.author = bookReference.author;
        this.quantity = bookReference.quantity;
    }

    // GETTERS && SETTERS

    public String getIsbn() {
        return this.isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;

    }

    @Override
    public String toString() {
        return "ISBN: " + this.isbn +
                "\t Title: " + this.title +
                "\t Author: " + this.author +
                "\t Quantity: " + this.quantity;
    }
}
