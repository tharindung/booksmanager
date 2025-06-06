package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.service.CountryServie;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/countries")
public class CountryController {

    private CountryServie countryService;

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto){

        CountryDto savedCountry = countryService.createCountry(countryDto);

        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){

        List<CountryDto> allCountries = countryService.getAllCountries();

        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable("id") Long countryId){

        CountryDto foundCountry = countryService.getCountryById(countryId);

        return new ResponseEntity<>(foundCountry, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable("id") Long countryId, @RequestBody CountryDto countryDto)
    {
        CountryDto updatedCountry = countryService.updateCountry(countryId, countryDto);

        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Long countryId)
    {
        countryService.deleteCountry(countryId);

        return new ResponseEntity<>("Country with ID : " + countryId + " deleted successfully !", HttpStatus.OK);
    }
}
