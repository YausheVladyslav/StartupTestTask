package com.example.testtask.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonIdRequest {

    @NotNull
    private long id;
}
