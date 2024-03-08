package me.example.service;

import me.example.domain.repository.CarRepository;
import me.example.domain.repository.PersonRepository;
import me.example.service.exception.PersonNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class CarServiceTest {

    private static final Long TEST_ID = 1L;

    @Mock
    private CarRepository carRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private CarService underTest;

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
}
