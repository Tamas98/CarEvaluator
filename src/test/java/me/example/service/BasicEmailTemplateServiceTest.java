package me.example.service;

import me.example.domain.Car;
import me.example.domain.cache.EmailTemplateCache;
import me.example.domain.repository.PersonRepository;
import me.example.service.exception.PersonNotFoundException;
import me.example.service.properties.TemplateProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static me.example.service.utility.CarEvaluatorTestUtility.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class BasicEmailTemplateServiceTest {

    private static final String TEMPLATE = """
        <name>, <birth>, <country>
        
        <begin>
        
        <brand>
        <type>
        <plate>
        <value>
        <driven>
        <year>
   
        <end>     
        """;

    @Mock
    private TemplateProperties templateProperties;

    @Mock
    private EmailTemplateCache emailTemplateCache;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private BasicEmailTemplateService underTest;

    @BeforeEach
    public void setUp() {
        underTest = null;
        initMocks(this);
    }

    @Test
    public void testConstructEmailShouldThrowExceptionWhenNoPersonPresent() {
        // GIVEN
        given(personRepository.findById(TEST_ID)).willReturn(Optional.empty());

        // WHEN
        // THEN
        Assertions.assertThrows(PersonNotFoundException.class, () -> underTest.fillEmailTemplateForPerson(TEST_ID));
    }

    @Test
    public void testConstructEmailShouldCreateEmailFromTemplate() {
        // GIVEN
        given(personRepository.findById(TEST_ID)).willReturn(Optional.of(person()));
        given(emailTemplateCache.getEmailTemplateByLanguageId(TEST_ID)).willReturn(TEMPLATE);
        setUpTemplateProperties();

        // WHEN
        var actual = underTest.fillEmailTemplateForPerson(TEST_ID);

        // THEN
        assertionsForTemplate(actual);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCars")
    public void testConstructEmailShouldCreateEmailFromTemplateWithoutNotEligableCars(List<Car> cars) {
        // GIVEN
        var person = person();
        person.setCarList(cars);
        given(personRepository.findById(TEST_ID)).willReturn(Optional.of(person));
        given(emailTemplateCache.getEmailTemplateByLanguageId(TEST_ID)).willReturn(TEMPLATE);
        setUpTemplateProperties();

        // WHEN
        var actual = underTest.fillEmailTemplateForPerson(TEST_ID);

        // THEN
        Assertions.assertFalse(actual.contains(BRAND));
        Assertions.assertFalse(actual.contains(TYPE));
        Assertions.assertFalse(actual.contains(PLATE_NUMBER));
        Assertions.assertFalse(actual.contains(CALCULATED_VALUE.toString()));
        Assertions.assertFalse(actual.contains(DRIVEN_DISTANCE.toString()));
        Assertions.assertFalse(actual.contains(YEAR_OF_MANUFACTURE.toString()));
        Assertions.assertTrue(actual.contains(NAME));
        Assertions.assertTrue(actual.contains(COUNTRY));
        Assertions.assertTrue(actual.contains(DATE_OF_BIRTH.toString()));
    }

    private void setUpTemplateProperties() {
        given(templateProperties.getDistance()).willReturn("<driven>");
        given(templateProperties.getBrand()).willReturn("<brand>");
        given(templateProperties.getName()).willReturn("<name>");
        given(templateProperties.getPlate()).willReturn("<plate>");
        given(templateProperties.getCountry()).willReturn("<country>");
        given(templateProperties.getType()).willReturn("<type>");
        given(templateProperties.getYear()).willReturn("<year>");
        given(templateProperties.getValue()).willReturn("<value>");
        given(templateProperties.getDateOfBirth()).willReturn("<birth>");
        given(templateProperties.getLoopBegin()).willReturn("<begin>");
        given(templateProperties.getLoopEnd()).willReturn("<end>");
    }

    private void assertionsForTemplate(String actualResult) {
        Assertions.assertTrue(actualResult.contains(NAME));
        Assertions.assertTrue(actualResult.contains(COUNTRY));
        Assertions.assertTrue(actualResult.contains(BRAND));
        Assertions.assertTrue(actualResult.contains(TYPE));
        Assertions.assertTrue(actualResult.contains(DRIVEN_DISTANCE.toString()));
        Assertions.assertTrue(actualResult.contains(YEAR_OF_MANUFACTURE.toString()));
        Assertions.assertTrue(actualResult.contains(PLATE_NUMBER));
        Assertions.assertTrue(actualResult.contains(DATE_OF_BIRTH.toString()));
        Assertions.assertTrue(actualResult.contains(CALCULATED_VALUE.toString()));
        Assertions.assertFalse(actualResult.contains("<begin>"));
        Assertions.assertFalse(actualResult.contains("<end>"));
    }

    private static Stream<List<Car>> provideInvalidCars() {
        var carOne = car();
        carOne.setIsSent(true);
        var carTwo = car();
        carTwo.setCalculatedValue(0L);

        return Stream.of(
            List.of(carOne),
            List.of(carTwo)
        );
    }
}
