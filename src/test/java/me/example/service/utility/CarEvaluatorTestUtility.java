package me.example.service.utility;

import me.example.domain.Car;
import me.example.domain.Person;
import me.example.service.domain.CarDTO;

import java.time.LocalDate;
import java.util.List;

public class CarEvaluatorTestUtility {

    public static final Long TEST_ID = 1L;
    public static final Long CALCULATED_VALUE = 1501241L;
    public static final Long DRIVEN_DISTANCE = 1512512L;
    public static final Integer YEAR_OF_MANUFACTURE = 1999;
    public static final String TYPE = "TEST_TYPE";
    public static final String BRAND = "TEST_BRAND";
    public static final String PLATE_NUMBER = "TEST_PLATE";
    public static final String NAME = "TEST_NAME";
    public static final String COUNTRY = "TEST_COUNTRY";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2000,1,1);

    public static Car car() {
        return Car.builder()
            .carId(TEST_ID)
            .calculatedValue(CALCULATED_VALUE)
            .drivenDistance(DRIVEN_DISTANCE)
            .type(TYPE)
            .brand(BRAND)
            .plateNumber(PLATE_NUMBER)
            .yearOfManufacture(YEAR_OF_MANUFACTURE)
            .isSent(false)
            .build();
    }

    public static CarDTO carDTO() {
        return CarDTO.builder()
            .calculatedValue(CALCULATED_VALUE)
            .drivenDistance(DRIVEN_DISTANCE)
            .type(TYPE)
            .brand(BRAND)
            .plateNumber(PLATE_NUMBER)
            .yearOfManufacture(YEAR_OF_MANUFACTURE)
            .build();
    }

    public static Person person() {
        return Person.builder()
            .personId(TEST_ID)
            .name(NAME)
            .country(COUNTRY)
            .dateOfBirth(DATE_OF_BIRTH)
            .languageId(TEST_ID)
            .carList(List.of(car()))
            .build();
    }
}
