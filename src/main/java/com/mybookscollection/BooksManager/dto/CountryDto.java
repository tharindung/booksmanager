package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mybookscollection.BooksManager.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto{

    private Long countryId;

    @NotEmpty(message = "Country should not be empty !")
    private String country;

    //private Set<User> users = new HashSet<>();
    @JsonBackReference(value="userCountry")
    private Set<UserDto> users = new HashSet<>();

}
