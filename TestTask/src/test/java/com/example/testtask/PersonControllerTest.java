package com.example.testtask;

import com.example.testtask.controller.PersonController;
import com.example.testtask.exception.BadRequestException;
import com.example.testtask.request.PersonIdRequest;
import com.example.testtask.request.SavePersonRequest;
import com.example.testtask.responses.PersonResponse;
import com.example.testtask.service.PersonService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final LocalDate DATE = LocalDate.of(1999, 9, 18);
    private static final int AGE = Period.between(DATE, LocalDate.now()).getYears();

    @Autowired
    private PersonController personController;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPerson() {
        PersonIdRequest request = new PersonIdRequest();
        request.setId(ID);

        ResponseEntity<PersonResponse> response = personController.getPerson(request);

        verify(personService, times(1)).getPersonById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testBadRequest() {
        PersonIdRequest request = new PersonIdRequest();
        request.setId(ID);

        when(personService.getPersonById(ID)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> {
            ResponseEntity<PersonResponse> response = personController.getPerson(request);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        });

        verify(personService, times(1)).getPersonById(ID);
    }

    @Test
    public void testSavePerson() {
        SavePersonRequest request = new SavePersonRequest();
        request.setName(NAME);
        request.setSurname(SURNAME);
        request.setBirthday(DATE);

        ResponseEntity<Void> response = personController.savePerson(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(personService, times(1)).savePerson(
                request.getName(),request.getSurname(),request.getBirthday()
        );
    }
}
