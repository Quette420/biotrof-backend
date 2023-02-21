package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateEmployerRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
