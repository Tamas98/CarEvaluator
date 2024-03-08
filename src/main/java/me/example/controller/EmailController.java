package me.example.controller;

import lombok.RequiredArgsConstructor;
import me.example.service.CarEvaluatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final CarEvaluatorService carEvaluatorService;

    @GetMapping("/email/{id}")
    public String getTemplateForPerson(@PathVariable("id") String id) {
        return carEvaluatorService.constructEmail(Long.valueOf(id));
    }
}
