package com.example.testtask.service;

import com.example.testtask.entity.PersonEntity;
import com.example.testtask.exception.BadRequestException;
import com.example.testtask.repository.PersonRepository;
import com.example.testtask.responses.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public void savePerson(String name, String surname, LocalDate date) {
        PersonEntity person = new PersonEntity();
        person.setName(name);
        person.setSurname(surname);
        person.setBirthday(date);
        repository.save(person);
    }

    public PersonResponse getPersonById(long id) {
        Optional<PersonEntity> person = repository.findById(id);
        if (person.isEmpty()) {
            throw new BadRequestException("Person by id: " + id + ", is not found");
        } else {
            PersonEntity personById = person.get();
            return new PersonResponse(
                    personById.getName(),
                    personById.getSurname(),
                    Period.between(personById.getBirthday(), LocalDate.now()).getYears()
            );
        }
    }
}
