package com.pm.library.dao.interfaces;

import com.pm.library.model.Book;
import com.pm.library.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberInterface {
    public boolean add(Member member)
            throws SQLException;

    public boolean update(Member member)
            throws SQLException;
    public boolean delete(int id)
            throws SQLException;
    public  Member getMember(int id)
            throws SQLException;
    public List<Member> getMembers()
            throws SQLException;
}
