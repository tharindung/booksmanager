package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "countryId")
public class CountryDto{

    private Long countryId;

    @NotEmpty(message = "Country should not be empty !")
    private String country;

    //@JsonBackReference(value="userCountry")
    //private Set<UserDto> users = new HashSet<>();

    /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information - Anyway due to the annotation '@JsonBackReference' user details are not going to display in 'country' payload */
    //@JsonBackReference(value="userCountry")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    private Set<UserDisplayDto> users = new HashSet<>();
}
