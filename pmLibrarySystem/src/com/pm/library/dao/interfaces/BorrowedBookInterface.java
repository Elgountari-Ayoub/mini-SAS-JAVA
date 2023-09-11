package com.pm.library.dao.interfaces;

import com.pm.library.model.Book;
import com.pm.library.model.BorrowedBook;
import com.pm.library.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface BorrowedBookInterface {
    public boolean add(int memberId, int bookId, int borrowingDays)
    throws SQLException;

    public boolean update(BorrowedBook borrowedBook)
            throws SQLException;

    // The same what return do
    public boolean delete(int id)
            throws SQLException;

    public BorrowedBook getBorrowedBook(int id)
            throws SQLException;

    public  List<BorrowedBook> getBorrowedBooks()
            throws SQLException;

}
