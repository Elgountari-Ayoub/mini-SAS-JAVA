package com.pm.library.model;

import java.util.Date;

public class BorrowedBook {

    private int id;
    private int memberId;
    private int bookId;

    private Date borrowDate;
    private Date returnDate;

    public BorrowedBook() {
    }

    public BorrowedBook(BorrowedBook borrwedBook) {
        this.id = borrwedBook.id;
        this.memberId = borrwedBook.memberId;
        this.bookId = borrwedBook.bookId;
        this.borrowDate = borrwedBook.borrowDate;
        this.returnDate = borrwedBook.returnDate;
    }

    public BorrowedBook(int id, int memberId, int bookId, Date borrowDate, Date returnDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }


    public BorrowedBook(int memberId, int bookId, Date borrowDate, Date returnDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }



    // GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public int getMemberId() {
        return this.memberId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getBookId() {
        return this.bookId;
    }

    public void setborrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    public Date getBorrowDate() {
        return this.borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public Date getReturnDate() {
        return this.returnDate;
    }
    @Override
    public String toString() {
        return "Member Id: " + this.memberId +
                "\t Book Id: " + this.bookId +
                "\t Borrowed Date: " + this.borrowDate +
                "\t Return Date: " + this.returnDate;
    }

}
