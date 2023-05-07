package com.prockopev.libraryapp.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Book {
    private int bookId;
    private int personId;
    @NotEmpty(message = "Необходимо указать название книги")
    private String title;
    @NotEmpty(message = "Автор должен быть указан")
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublication;

    public Book(int bookId, String title, String author, Date dateOfPublication) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.dateOfPublication = dateOfPublication;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", personId=" + personId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateOfPublication=" + dateOfPublication +
                '}';
    }
}
