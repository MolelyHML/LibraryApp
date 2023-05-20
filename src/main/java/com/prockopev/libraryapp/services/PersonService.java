package com.prockopev.libraryapp.services;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Person;
import com.prockopev.libraryapp.repository.BooksRepository;
import com.prockopev.libraryapp.repository.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PeopleRepository peopleRepository;

    private final BooksRepository booksRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }

    public List<Book> findPersonBooks(int id) {
        Person owner = peopleRepository.findById(id).orElse(null);
        return booksRepository.findAllByOwner(owner);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPersonId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        Person personToDelete = peopleRepository.findById(id).orElse(null);
        peopleRepository.delete(Objects.requireNonNull(personToDelete));
    }
}
