package me.example.controller;

import lombok.RequiredArgsConstructor;
import me.example.service.CarService;
import me.example.service.domain.CarDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable("id") Long carId) {
        return carService.getCarById(carId);
    }

    @GetMapping("/person/{id}")
    public List<CarDTO> getPersonsCarList(@PathVariable("id") Long personId) {
        return carService.getAllCarsOfAPerson(personId);
    }
}
