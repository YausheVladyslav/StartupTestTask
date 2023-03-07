package com.example.testtask;

import com.example.testtask.entity.PersonEntity;
import com.example.testtask.repository.PersonRepository;
import com.example.testtask.request.PersonIdRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIntegrationTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final LocalDate DATE = LocalDate.of(1999, 9, 18);
    private static final int AGE = Period.between(DATE, LocalDate.now()).getYears();
    private static final long ID = 1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PersonRepository personRepository;

    @Test
    public void testSavePerson() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setId(ID);
        person.setName(NAME);
        person.setSurname(SURNAME);
        person.setBirthday(DATE);

        mockMvc.perform(post("/person/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPerson() throws Exception {
        PersonEntity person = new PersonEntity();
        person.setId(ID);
        person.setName(NAME);
        person.setSurname(SURNAME);
        person.setBirthday(DATE);
        personRepository.save(person);

        PersonIdRequest personIdRequest = new PersonIdRequest();
        personIdRequest.setId(person.getId());

        when(personRepository.findById(ID)).thenReturn(Optional.of(person));

        mockMvc.perform(get("/person/get-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personIdRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonNotFoundId() throws Exception {
        when(personRepository.findById(ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/person/get-user"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
