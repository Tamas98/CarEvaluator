package me.example.service;

import me.example.service.domain.CarDTO;

import java.util.List;

public interface CarService {
    List<CarDTO> getAllCarsOfAPerson(Long personId);
    CarDTO getCarById(Long cardId);
}
