package com.prockopev.libraryapp.repository;

import com.prockopev.libraryapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {


}
