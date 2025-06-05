package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.CountryDto;

import java.util.List;

public interface CountryServie {

    CountryDto createCountry(CountryDto countryDto);

    List<CountryDto> getAllCountries();

    CountryDto getCountryById(Long countryId);

    CountryDto updateCountry(Long countryId, CountryDto countryDto);

    void deleteCountry(Long countryId);
}
