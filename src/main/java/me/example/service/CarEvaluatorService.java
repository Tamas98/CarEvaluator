package me.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.example.domain.Car;
import me.example.domain.Person;
import me.example.domain.cache.EmailTemplateCache;
import me.example.domain.repository.PersonRepository;
import me.example.service.properties.TemplateProperties;
import me.example.service.exception.PersonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarEvaluatorService {

    private final TemplateProperties templateProperties;
    private final EmailTemplateCache emailTemplateCache;
    private final PersonRepository personRepository;

    public String constructEmail(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
        String template = getTemplateWithFilledHeader(person);
        return fillTemplate(template, person.getCarSet());
    }

    private String fillTemplate(String template, List<Car> carSet) {
        StringBuilder sb = new StringBuilder(template);
        var startPosition = sb.indexOf(templateProperties.getLoopBegin()) + templateProperties.getLoopBegin().length();
        String carDataSub = sb.substring(startPosition, sb.indexOf(templateProperties.getLoopEnd()));
        String carDataListFilled = createCarDataList(carDataSub, carSet);
        return createEmail(carDataListFilled, template);
    }

    private String createCarDataList(String carDataSub, List<Car> carSet) {
       return carSet.stream().filter(car -> car.getCalculatedValue() > 0 && !car.getIsSent()).map(car -> {
           log.debug("Constructing car data for car with id: {}", car.getCarId());
            String carData = carDataSub.replace(templateProperties.getBrand(), car.getBrand());
            carData = carData.replace(templateProperties.getType(), car.getType());
            carData = carData.replace(templateProperties.getPlate(), car.getPlateNumber());
            carData = carData.replace(templateProperties.getValue(), String.valueOf(car.getCalculatedValue()));
            carData = carData.replace(templateProperties.getYear(), String.valueOf(car.getYearOfManufacture()));
            carData = carData.replace(templateProperties.getDistance(), String.valueOf(car.getDrivenDistance()));
            return carData;
        }).collect(Collectors.joining("\n\n"));
    }

    private String createEmail(String carDataList, String template) {
        var headerUntil = template.indexOf(templateProperties.getLoopBegin());
        var carDataListUntil = template.indexOf(templateProperties.getLoopEnd()) + templateProperties.getLoopEnd().length();
        return template.substring(0, headerUntil) + carDataList + template.substring(carDataListUntil);
    }

    private String getTemplateWithFilledHeader(Person person) {
        log.info("Constructing email for person with id {} and language id: {}", person.getPersonId(), person.getLanguageId());
        String template = emailTemplateCache.getEmailTemplateByLanguageId(person.getLanguageId());
        template = template.replace(templateProperties.getName(), person.getName());
        template = template.replace(templateProperties.getCountry(), person.getCountry());
        return template.replace(templateProperties.getDateOfBirth(), person.getDateOfBirth().toString());
    }
}
