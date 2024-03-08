package me.example.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "evaluator.template.parameters")
public class TemplateProperties {

    private String loopBegin;
    private String loopEnd;
    private String brand;
    private String type;
    private String year;
    private String plate;
    private String distance;
    private String value;
    private String name;
    private String country;
    private String dateOfBirth;
}
