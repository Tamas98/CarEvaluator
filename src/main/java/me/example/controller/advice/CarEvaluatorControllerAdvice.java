package me.example.controller.advice;

import me.example.service.exception.CarNotFoundException;
import me.example.service.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CarEvaluatorControllerAdvice {

    @ExceptionHandler({ CarNotFoundException.class, PersonNotFoundException.class })
    protected ResponseEntity<String> handleNotFoundException(
        RuntimeException ex, WebRequest request) {
        String id = request.getParameter("id");
        return new ResponseEntity<>("No database entry found with the given ID", HttpStatus.BAD_REQUEST);
    }
}
