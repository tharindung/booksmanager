package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.entity.Country;
import com.mybookscollection.BooksManager.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class CountryServiceTests {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country country;
    private CountryDto countryDto;

    @BeforeEach
    public void setup()
    {
        country = Country.builder().
                    countryId(1L).
                    country("Canada").build();

        countryDto = CountryDto.builder().
                        countryId(1L).
                        country("Canada").build();

        //MockitoAnnotations.openMocks(this);
    }

    //Junit test for createCountry method
    @DisplayName("Junit test for createCountry method")
    @Test
    public void givenCountryDtoObject_whenCreateCountry_thenReturnSavedCountryDtoObject(){

        //given - precondition or setup
        given(countryRepository.save(country)).willReturn(country);

        doReturn(country)
                .when(modelMapper).map(countryDto, Country.class);
        doReturn(countryDto)
                .when(modelMapper).map(country, CountryDto.class);

        //when - action or the behavior we're testing
        CountryDto savedCountry = countryService.createCountry(countryDto);

        //then - verify the output
        Assertions.assertThat(savedCountry).isNotNull();
    }

    //Junit test for getAllCountries method positive scenario
    @DisplayName("Junit test for getAllCountries method positive scenario")
    @Test
    public void givenCountryList_whenGetAllCountries_thenReturnCountryDtoList(){

        //given - precondition or setup
        Country country1 = Country.builder().
                                countryId(2L).
                                country("Sri Lanka").build();

        given(countryRepository.findAll()).willReturn(List.of(country, country1));

        //when - action or the behavior we're testing
        List<CountryDto> allCountries = countryService.getAllCountries();

        //then - verify the output
        Assertions.assertThat(allCountries).isNotNull();
        Assertions.assertThat(allCountries.size()).isEqualTo(2);
    }

    //Junit test for getAllCountries method negative scenario
    @DisplayName("Junit test for getAllCountries method negative scenario")
    @Test
    public void givenEmptyCountryList_whenGetAllCountries_thenReturnEmptyCountryDtoList(){

        //given - precondition or setup
        given(countryRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<CountryDto> allCountries = countryService.getAllCountries();

        //then - verify the output
        Assertions.assertThat(allCountries).isEmpty();
        Assertions.assertThat(allCountries.size()).isEqualTo(0);
    }

    //Junit test for get countryById method
    @DisplayName("Junit test for getCountryById method")
    @Test
    public void givenCountryId_whenGetCountryById_thenReturnCountryDtoObject(){

        //given - precondition or setup
        given(countryRepository.findById(country.getCountryId())).willReturn(Optional.of(country));

        doReturn(countryDto)
                .when(modelMapper).map(country, CountryDto.class);

        //when - action or the behavior we're testing
        CountryDto returnedCountry = countryService.getCountryById(country.getCountryId());

        //then - verify the output
        Assertions.assertThat(returnedCountry).isNotNull();
    }

    //Junit test for updateCountry method
    @DisplayName("Junit test for updateCountry method")
    @Test
    public void givenCountryDtoObject_whenUpdateCountry_thenReturnUpdatedCountryDtoObject(){

        //given - precondition or setup
        given(countryRepository.findById(country.getCountryId())).willReturn(Optional.of(country));

        given(countryRepository.save(country)).willReturn(country);

        doReturn(countryDto)
                .when(modelMapper).map(country, CountryDto.class);

        country.setCountry("Japan");

        countryDto.setCountry("Japan");

        //when - action or the behavior we're testing
        CountryDto updatedCountry = countryService.updateCountry(countryDto.getCountryId(), countryDto);

        //then - verify the output
        Assertions.assertThat(updatedCountry.getCountry()).isEqualTo("Japan");
    }

    //Junit test for deleteCountry method
    @DisplayName("Junit test for deleteCountry method")
    @Test
    public void givenCountryId_whenDeleteCountry_thenReturnNothing(){

        //given - precondition or setup
        given(countryRepository.findById(country.getCountryId())).willReturn(Optional.of(country));

        willDoNothing().given(countryRepository).deleteById(country.getCountryId());

        //when - action or the behavior we're testing
        countryService.deleteCountry(countryDto.getCountryId());

        //then - verify the output
        verify(countryRepository, times(1)).deleteById(countryDto.getCountryId());
    }
}
