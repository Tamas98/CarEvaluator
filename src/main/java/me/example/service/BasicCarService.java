package me.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.example.domain.Car;
import me.example.domain.Person;
import me.example.domain.repository.CarRepository;
import me.example.domain.repository.PersonRepository;
import me.example.service.domain.CarDTO;
import me.example.service.exception.CarNotFoundException;
import me.example.service.exception.PersonNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicCarService implements CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final ConversionService conversionService;

    public List<CarDTO> getAllCarsOfAPerson(Long personId) {
        log.info("Querying cars for person with id: {}", personId);
        Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
        return person.getCarList().stream().map(car -> conversionService.convert(car, CarDTO.class)).toList();
    }

    public CarDTO getCarById(Long cardId) {
        log.info("Querying car with id: {}", cardId);
        Car car = carRepository.findById(cardId).orElseThrow(CarNotFoundException::new);
        return conversionService.convert(car, CarDTO.class);
    }
}
