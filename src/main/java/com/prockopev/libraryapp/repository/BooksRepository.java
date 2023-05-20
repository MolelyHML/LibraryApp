package com.prockopev.libraryapp.repository;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOwner(Person person);

}
