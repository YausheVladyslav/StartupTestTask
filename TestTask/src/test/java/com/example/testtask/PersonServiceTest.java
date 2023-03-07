package com.example.testtask;

import com.example.testtask.entity.PersonEntity;
import com.example.testtask.exception.BadRequestException;
import com.example.testtask.repository.PersonRepository;
import com.example.testtask.responses.PersonResponse;
import com.example.testtask.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final LocalDate DATE = LocalDate.of(1999, 9, 18);
    private static final int AGE = Period.between(DATE, LocalDate.now()).getYears();
    private static final long ID = 1;

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void testSavePerson() {
        personService.savePerson(NAME, SURNAME, DATE);

        verify(personRepository, times(1)).save(any(PersonEntity.class));
    }

    @Test
    public void testPersonNotFound() {
        when(personRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> {
            PersonResponse response = personService.getPersonById(ID);
            assertNull(response);
        });

        verify(personRepository, times(1)).findById(ID);
    }

    @Test
    public void testPersonExists() {
        PersonEntity person = new PersonEntity();
        person.setId(ID);
        person.setName(NAME);
        person.setSurname(SURNAME);
        person.setBirthday(DATE);

        when(personRepository.findById(ID)).thenReturn(Optional.of(person));

        PersonResponse response = personService.getPersonById(ID);

        assertEquals(NAME, response.getName());
        assertEquals(SURNAME, response.getSurname());
        assertEquals(AGE, response.getAge());

        verify(personRepository, times(1)).findById(ID);
    }
}
