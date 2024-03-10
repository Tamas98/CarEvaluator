package me.example.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static me.example.service.utility.CarEvaluatorTestUtility.car;
import static me.example.service.utility.CarEvaluatorTestUtility.carDTO;

public class CarToCarDTOConverterTest {

    private final CarToCarDTOConverter underTest = new CarToCarDTOConverter();

    @Test
    public void testConvertShouldConvertCarIntoCarDTO() {
        // GIVEN

        // WHEN
        var result = underTest.convert(car());

        // THEN
        Assertions.assertEquals(carDTO(), result);
    }



}
