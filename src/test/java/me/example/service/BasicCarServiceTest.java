package me.example.service;

import me.example.domain.repository.CarRepository;
import me.example.domain.repository.PersonRepository;
import me.example.service.domain.CarDTO;
import me.example.service.exception.CarNotFoundException;
import me.example.service.exception.PersonNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;

import static me.example.service.utility.CarEvaluatorTestUtility.TEST_ID;
import static me.example.service.utility.CarEvaluatorTestUtility.car;
import static me.example.service.utility.CarEvaluatorTestUtility.carDTO;
import static me.example.service.utility.CarEvaluatorTestUtility.person;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class BasicCarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private BasicCarService underTest;

    @BeforeEach
    public void setUp() {
        underTest = null;
        initMocks(this);
    }

    @Test
    public void testGetAllCarsOfAPersonShouldThrowExceptionWhenNoPersonPresent() {
        // GIVEN
        given(personRepository.findById(TEST_ID)).willReturn(Optional.empty());

        // WHEN
        // THEN
        Assertions.assertThrows(PersonNotFoundException.class, () -> underTest.getAllCarsOfAPerson(TEST_ID));
    }

    @Test
    public void testGetAllCarsOfAPersonShouldReturnAllCarsAssociated() {
        // GIVEN
        given(personRepository.findById(TEST_ID)).willReturn(Optional.of(person()));
        given(conversionService.convert(car(), CarDTO.class)).willReturn(carDTO());

        // WHEN
        var result = underTest.getAllCarsOfAPerson(TEST_ID);

        // THEN
        Assertions.assertEquals(result, List.of(carDTO()));
    }

    @Test
    public void testGetCarByIdShouldThrowExceptionWhenNoCarPresent() {
        // GIVEN
        given(carRepository.findById(TEST_ID)).willReturn(Optional.empty());

        // WHEN
        // THEN
        Assertions.assertThrows(CarNotFoundException.class, () -> underTest.getCarById(TEST_ID));
    }

    @Test
    public void testGetCarByIdShouldReturnCarWithGivenId() {
        // GIVEN
        given(carRepository.findById(TEST_ID)).willReturn(Optional.of(car()));
        given(conversionService.convert(car(), CarDTO.class)).willReturn(carDTO());

        // WHEN
        var result = underTest.getCarById(TEST_ID);

        // THEN
        Assertions.assertEquals(result, carDTO());
    }
}
