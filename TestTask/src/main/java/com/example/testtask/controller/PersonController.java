package com.example.testtask.controller;

import com.example.testtask.request.PersonIdRequest;
import com.example.testtask.request.SavePersonRequest;
import com.example.testtask.responses.PersonResponse;
import com.example.testtask.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    @GetMapping("/get-user")
    public ResponseEntity<PersonResponse> getPerson(@Valid @RequestBody PersonIdRequest request) {
        return ResponseEntity.ok(service.getPersonById(request.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> savePerson(@Valid @RequestBody SavePersonRequest person) {
        service.savePerson(person.getName(), person.getSurname(), person.getBirthday());
        return ResponseEntity.ok().build();
    }
}
