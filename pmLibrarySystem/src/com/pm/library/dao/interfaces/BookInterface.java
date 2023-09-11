package com.pm.library.dao.interfaces;

import com.pm.library.model.Book;


import java.sql.SQLException;
import java.util.List;

public interface BookInterface {
    public boolean add(Book book)
            throws SQLException;

    public boolean update(Book book)
            throws SQLException;

    public boolean delete(int id)
            throws SQLException;

    public Book getBook(int id)
            throws SQLException;

    public  List<Book> getBooks()
            throws SQLException;

}
