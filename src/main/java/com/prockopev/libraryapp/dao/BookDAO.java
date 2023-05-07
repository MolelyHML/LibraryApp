package com.prockopev.libraryapp.dao;

import com.prockopev.libraryapp.model.Book;
import com.prockopev.libraryapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public void save(Book newBook) {
        jdbcTemplate.update("INSERT INTO book(title, person_id, author, date_of_publication) VALUES (?, ?, ?, ?)",
                newBook.getTitle(),
                null,
                newBook.getAuthor(),
                newBook.getDateOfPublication());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book where book_id=?",
                new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?",id);
    }

    public void update(Book updatedBook, int id) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, date_of_publication=? WHERE book_id=?",
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getDateOfPublication(),
                id);
    }

    public List<Person> getPerson(int personId, int bookId) {
        return jdbcTemplate.query("SELECT * FROM Person JOIN Book on Person.person_id = Book.person_id WHERE book.person_id=? AND book.book_id=?",
                new Object[]{personId, bookId},new BeanPropertyRowMapper<>(Person.class));
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?",id);
    }

    public void catchBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",personId, bookId);
    }
}
