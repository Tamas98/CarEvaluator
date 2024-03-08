package me.example.service.converter;

import me.example.domain.Car;
import me.example.service.domain.CarDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarToCarDTOConverter implements Converter<Car, CarDTO> {
    @Override
    public CarDTO convert(Car source) {
        return CarDTO.builder()
            .drivenDistance(source.getDrivenDistance())
            .brand(source.getBrand())
            .plateNumber(source.getPlateNumber())
            .calculatedValue(source.getCalculatedValue())
            .type(source.getType())
            .yearOfManufacture(source.getYearOfManufacture())
            .build();
    }
}
