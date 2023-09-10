package com.pm.library.dao.interfaces;

import com.pm.library.model.BookReference;

import java.sql.SQLException;
import java.util.List;

public interface BookReferenceInterface {
    public boolean add(BookReference bookReference)
            throws SQLException;

    public boolean update(BookReference bookReference)
            throws SQLException;
    public boolean delete(String isbn)
            throws SQLException;
    public BookReference getBookReference(String isbn)
            throws SQLException;
    public List<BookReference> getBooksReference()
            throws SQLException;

}
