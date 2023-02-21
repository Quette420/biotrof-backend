package ru.volgau.graduatework.biotrofbackend.model.response;

import lombok.Data;
import ru.volgau.graduatework.biotrofbackend.dictionary.Role;
import ru.volgau.graduatework.biotrofbackend.dictionary.Status;

@Data
public class EmployerRegistrationResponse {

    private String uuid;

    private String username;

    private Role role;

    private Status status;
}
