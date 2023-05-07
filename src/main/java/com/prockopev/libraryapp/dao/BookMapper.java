package com.prockopev.libraryapp.dao;

import com.prockopev.libraryapp.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setPersonId(rs.getInt("person_id"));
        book.setAuthor(rs.getString("author"));
        book.setDateOfPublication(rs.getDate("date_of_publication"));

        return book;
    }
}
