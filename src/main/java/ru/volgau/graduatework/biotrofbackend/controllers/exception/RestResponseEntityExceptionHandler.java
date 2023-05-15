package ru.volgau.graduatework.biotrofbackend.controllers.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.volgau.graduatework.biotrofbackend.exceptions.UserAlreadyExistsException;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public final ResponseEntity<Object> handleUserAlreadyExistsException(final RuntimeException e, ServletWebRequest request) {
        log.error("UserAlreadyExists ", e);
        final ApiError apiError = message(e, request);
        return handleExceptionInternal(e, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ApiError message(final Exception ex, ServletWebRequest request) {
        final String devMessage = ex.getClass().getSimpleName();
        final String message = ex.getMessage() == null ? devMessage : ex.getMessage();
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequest().getRequestURI(), message, devMessage);
    }
}
