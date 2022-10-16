package com.github.shortlinks.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ApiExceptionDescription {
    private final String message;
    private final Throwable throwable;
    private final ZonedDateTime zonedDateTime;
}
