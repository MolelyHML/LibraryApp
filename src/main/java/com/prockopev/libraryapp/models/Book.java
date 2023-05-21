package com.prockopev.libraryapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title")
    @NotEmpty(message = "Необходимо указать название книги")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Автор должен быть указан")
    private String author;

    @Column(name = "date_of_publication")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublication;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "catch_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date catchTime;

    @Transient
    private boolean overdue;

    @Transient
    private final long tenDays = 10 * 86400000;

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public void checkOverdue() {
        if(catchTime != null)
            setOverdue(System.currentTimeMillis() >= catchTime.getTime() + tenDays);

    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateOfPublication=" + dateOfPublication +
                '}';
    }
}
