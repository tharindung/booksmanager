package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.service.CountryServie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/countries")
@Tag(name = "Countries", description = "CRUD operations for countries")
public class CountryController {

    private CountryServie countryService;

    @Operation(
            summary = "Create a new country",
            description = "Creates and returns a new country",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Country created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CountryDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody @Valid CountryDto countryDto){

        CountryDto savedCountry = countryService.createCountry(countryDto);

        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all countries",
            description = "Returns a list of all countries",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of countries",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CountryDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){

        List<CountryDto> allCountries = countryService.getAllCountries();

        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a country by ID",
            description = "Returns a country by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CountryDto.class))),
                    @ApiResponse(responseCode = "404", description = "Country not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable("id") Long countryId){

        CountryDto foundCountry = countryService.getCountryById(countryId);

        return new ResponseEntity<>(foundCountry, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a country",
            description = "Updates and returns a country by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CountryDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Country not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable("id") Long countryId, @RequestBody @Valid CountryDto countryDto)
    {
        CountryDto updatedCountry = countryService.updateCountry(countryId, countryDto);

        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a country",
            description = "Deletes a country by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country deleted"),
                    @ApiResponse(responseCode = "404", description = "Country not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Long countryId)
    {
        countryService.deleteCountry(countryId);

        return new ResponseEntity<>("Country with ID : " + countryId + " deleted successfully !", HttpStatus.OK);
    }
}
