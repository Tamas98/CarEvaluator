package me.example.domain.cache;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.example.domain.EmailTemplate;
import me.example.domain.repository.EmailTemplateRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@RequiredArgsConstructor
public class EmailTemplateCache {

    private final EmailTemplateRepository emailTemplateRepository;

    private final Map<Long, String> templateMap = new HashMap<>();

    public String getEmailTemplateByLanguageId(Long languageId) {
        return templateMap.get(languageId);
    }

    @PostConstruct
    private void setUp() {
        emailTemplateRepository.findAll().forEach(emailTemplate ->
            templateMap.put(emailTemplate.getLanguageId(), emailTemplate.getText()));
    }
}
