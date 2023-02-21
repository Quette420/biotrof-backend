package ru.volgau.graduatework.biotrofbackend.controllers.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {

    private final int status;
    private final Date timestamp;
    private String path;
    private String message;
    private String developerMessage;

    public ApiError(int status, String path, String message, String developerMessage) {
        this.status = status;
        this.path = path;
        this.message = message;
        this.developerMessage = developerMessage;
        this.timestamp = new Date();
    }
}
