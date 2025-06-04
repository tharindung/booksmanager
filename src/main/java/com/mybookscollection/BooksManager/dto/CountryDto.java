package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private Long countryId;
    private String country;
    private Set<User> users = new HashSet<>();
}
