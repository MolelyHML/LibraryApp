package com.prockopev.libraryapp.dao;

import com.prockopev.libraryapp.model.Book;
import com.prockopev.libraryapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, birth_date) VALUES (?,?)",
                person.getFullName(),
                person.getBirthDate());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?,birth_date=? WHERE person_id=?",
                updatedPerson.getFullName(),
                updatedPerson.getBirthDate(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?",id);
    }

    public List<Book> getPersonBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM Person JOIN Book on Person.person_id = book.person_id WHERE book.person_id=?",
                new Object[]{id}, new BookMapper());
    }
}
