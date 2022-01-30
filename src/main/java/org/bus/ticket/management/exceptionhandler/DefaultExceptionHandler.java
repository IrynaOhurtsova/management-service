package org.bus.ticket.management.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.bus.ticket.management.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorMessageDto(exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleException(IllegalStateException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorMessageDto(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().build();
    }
}
