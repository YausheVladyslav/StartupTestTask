package com.example.testtask.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonResponse {

    private String name;
    private String surname;
    private int age;
}
