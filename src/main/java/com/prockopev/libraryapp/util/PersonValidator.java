package com.prockopev.libraryapp.util;

import com.prockopev.libraryapp.models.Person;
import com.prockopev.libraryapp.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        long year = 31557600000L;
        long needYear = System.currentTimeMillis() - (15*year);

        if(person.getFullName() == null)
            errors.rejectValue("fullName", "", "ФИО не должно быть пустым!");

        if(person.getBirthDate() == null )
            errors.rejectValue("birthDate", "", "Дата рождения должна быть указана!");

        if(person.getBirthDate().getTime() >= needYear) {
            errors.rejectValue("birthDate", "", "Вам должно быть больше 15 лет!");
        }

    }
}
