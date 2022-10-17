package com.github.shortlinks.controller.advice;

import com.github.shortlinks.exceptions.LinkNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LinkNotFoundException.class)
    public final ResponseEntity<ApiExceptionDescription> handleLinkNotFoundException(Exception ex, WebRequest request) {
        ApiExceptionDescription description = new ApiExceptionDescription(ex.getMessage(), ex, ZonedDateTime.now());
        return new ResponseEntity<>(description, new HttpHeaders(), BAD_REQUEST);
    }
}
