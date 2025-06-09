package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.entity.Country;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryServie{

    private CountryRepository countryRepository;

    private ModelMapper modelMapper;

    @Override
    public CountryDto createCountry(CountryDto countryDto) {

        Country newCountry = modelMapper.map(countryDto, Country.class);

        Country savedCountry = countryRepository.save(newCountry);

        return modelMapper.map(savedCountry, CountryDto.class);
    }

    @Override
    public List<CountryDto> getAllCountries() {

        List<Country> allCountries = countryRepository.findAll();

        return allCountries.stream().map((c)->modelMapper.map(c, CountryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CountryDto getCountryById(Long countryId) {

        //Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException("Country with ID : "+countryId+" does not exist !"));
        Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException("Country", "countryId", countryId));

        return modelMapper.map(foundCountry, CountryDto.class);
    }

    @Override
    public CountryDto updateCountry(Long countryId, CountryDto countryDto) {

        //Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException(("Country with ID : "+countryId+" does not exist !")));
        Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException("Country", "countryId", countryId));

        foundCountry.setCountry(countryDto.getCountry());
        foundCountry.setUsers(countryDto.getUsers());

        Country updatedCountry = countryRepository.save(foundCountry);

        return modelMapper.map(updatedCountry, CountryDto.class);
    }

    @Override
    public void deleteCountry(Long countryId) {

        //Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException("Country with ID : "+countryId+" does not exist !"));
        Country foundCountry = countryRepository.findById(countryId).orElseThrow(()->new ResourceNotFoundException("Country", "countryId", countryId));

        countryRepository.deleteById(countryId);

    }
}
