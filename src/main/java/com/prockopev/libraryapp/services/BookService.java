package com.prockopev.libraryapp.services;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Person;
import com.prockopev.libraryapp.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;



    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
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
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void free(int id) {
        Book bookForFree = booksRepository.findById(id).orElse(null);

        if(bookForFree != null)
            bookForFree.setOwner(null);

        save(bookForFree);
    }

    @Transactional
    public void catchBook(int bookId, Person person) {
        booksRepository.findById(bookId).ifPresent(bookForCatch -> bookForCatch.setOwner(person));

    }

    @Transactional
    public void delete(int id) {
        booksRepository.delete(Objects.requireNonNull(booksRepository.findById(id).orElse(null)));
    }

    public Book findByTitleStartingWith(String startingWith) {
        return booksRepository.findByTitleStartingWith(startingWith).orElse(null);
    }
}