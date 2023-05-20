package com.prockopev.libraryapp.repository;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {


}
