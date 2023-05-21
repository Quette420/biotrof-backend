package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateEmailRequest {

    @NotBlank
    private String uuid;

    @NotBlank
    private String email;
}
