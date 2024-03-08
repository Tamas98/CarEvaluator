package me.example.controller;

import lombok.RequiredArgsConstructor;
import me.example.service.CarService;
import me.example.service.domain.CarDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/car/{id}")
    public CarDTO getCarById(@PathVariable("id") String carId) {
        return carService.getCarById(Long.valueOf(carId));
    }

    @GetMapping("/car/person/{id}")
    public List<CarDTO> getPersonsCarList(@PathVariable("id") String personId) {
        return carService.getAllCarsOfAPerson(Long.valueOf(personId));
    }
}
