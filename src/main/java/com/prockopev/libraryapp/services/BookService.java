package com.prockopev.libraryapp.services;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Genre;
import com.prockopev.libraryapp.models.Person;
import com.prockopev.libraryapp.repository.BooksRepository;
import com.prockopev.libraryapp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, GenreRepository genreRepository) {
        this.booksRepository = booksRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(String sort) {
        return booksRepository.findAll(Sort.by(sort));
    }

    public List<Book> findAll(int page, int itemsPerPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> findAll(int page, int itemsPerPage, String sort) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(sort))).getContent();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Book findById(int id) {
        Book book = booksRepository.findById(id).orElse(null);

        if(book != null)
            book.checkOverdue();

        return book;
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void free(int id) {
        Book bookForFree = booksRepository.findById(id).orElse(null);

        if(bookForFree != null) {
            bookForFree.setOwner(null);
            bookForFree.setCatchTime(null);
        }

        save(bookForFree);
    }

    public Optional<Genre> findGenreByName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    @Transactional
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Transactional
    public void catchBook(int bookId, Person person) {
        Book catchedBook = booksRepository.findById(bookId).orElse(null);

        if(catchedBook != null) {
            catchedBook.setCatchTime(new Date());
            catchedBook.setOwner(person);
        }

    }

    @Transactional
    public void delete(int id) {
        booksRepository.delete(Objects.requireNonNull(booksRepository.findById(id).orElse(null)));
    }

    public Book findByTitleStartingWith(String startingWith) {
        return booksRepository.findByTitleStartingWith(startingWith).orElse(null);
    }
}
