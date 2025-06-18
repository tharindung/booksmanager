package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.Country;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CountryRepositoryTests {

    @Autowired
    private CountryRepository countryRepository;

    private Country country;

    @BeforeEach
    public void setup()
    {
        country = Country.builder().country("Canada").build();
    }

    //Junit test for save country operation
    @DisplayName("Junit test for save country operation")
    @Test
    public void givenCountryObject_whenSave_thenReturnSavedCountryObject(){
        //given - precondition or setup
        //Country country = Country.builder().country("Canada").build();

        //when - action or the behavior we're testing
        Country savedCountry = countryRepository.save(country);

        //then - verify the output
        Assertions.assertThat(savedCountry).isNotNull();
        Assertions.assertThat(savedCountry.getCountryId()).isGreaterThan(0);
    }

    //Junit test for get all countries operation
    @DisplayName("Junit test for get all countries operation")
    @Test
    public void givenCountriesList_whenFindAll_thenReturnCountriesList(){

        //given - precondition or setup
        Country country1 = Country.builder().country("Sri Lanka").build();
        //Country country2 = Country.builder().country("Australia").build();

        countryRepository.save(country);
        countryRepository.save(country1);

        //when - action or the behavior we're testing
        List<Country> countriesList = countryRepository.findAll();

        //then - verify the output
        Assertions.assertThat(countriesList).isNotNull();
        Assertions.assertThat(countriesList.size()).isEqualTo(2);
    }

    //Junit test for get country by id operation
    @DisplayName("Junit test for get country by id operation")
    @Test
    public void givenCountryObject_whenFindById_thenReturnCountryObject(){

        //given - precondition or setup
        //Country country = Country.builder().country("England").build();

        countryRepository.save(country);

        //when - action or the behavior we're testing
        Country returnedCountry = countryRepository.findById(country.getCountryId()).get();

        //then - verify the output
        Assertions.assertThat(returnedCountry).isNotNull();
        Assertions.assertThat(returnedCountry.getCountry()).isEqualTo("Canada");
    }

    //Junit test for update country operation
    @DisplayName("Junit test for update country operation")
    @Test
    public void givenCountryObject_whenUpdateCountry_thenReturnUpdatedCountryObject()
    {
        //Given - precondition or setup
        //Country country = Country.builder().country("USA").build();

        countryRepository.save(country);

        //When - action or the behavior we're testing

        Country savedCountry = countryRepository.findById(country.getCountryId()).get();

        savedCountry.setCountry("India");

        Country updatedCountry = countryRepository.save(savedCountry);

        //Then - verify the output
        Assertions.assertThat(updatedCountry).isNotNull();
        Assertions.assertThat(updatedCountry.getCountry()).isEqualTo("India");
    }

    //Junit test for delete country operation
    @DisplayName("Junit test for delete country operation")
    @Test
    public void givenCountryObject_whenDeleteById_thenDeleteCountryObject(){

        //Given - precondition or setup
        //Country country = Country.builder().country("New Zealand").build();

        countryRepository.save(country);

        //When - action or the behavior we're testing
        countryRepository.deleteById(country.getCountryId());

        Optional<Country> optionalCountry = countryRepository.findById(country.getCountryId());

        //Then - verify the output
        Assertions.assertThat(optionalCountry).isEmpty();
    }
}
