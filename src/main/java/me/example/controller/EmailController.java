package me.example.controller;

import lombok.RequiredArgsConstructor;
import me.example.service.EmailTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping("/{id}")
    public String getTemplateForPerson(@PathVariable("id") Long id) {
        return emailTemplateService.fillEmailTemplateForPerson(id);
    }
}
