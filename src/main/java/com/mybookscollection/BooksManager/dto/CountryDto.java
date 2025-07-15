package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO representing a Country")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "countryId")
public class CountryDto{

    @Schema(
            description = "Unique identifier for the country",
            example = "101",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long countryId;

    @NotEmpty(message = "Country should not be empty !")
    @Schema(
            description = "Name of the country",
            example = "Canada",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String country;

    //@JsonBackReference(value="userCountry")
    //private Set<UserDto> users = new HashSet<>();

    /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information - Anyway due to the annotation '@JsonBackReference' user details are not going to display in 'country' payload */
    //@JsonBackReference(value="userCountry")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    @Schema(
            description = "List of users associated with the country (ignored in JSON)",
            hidden = true
    )
    private Set<UserDisplayDto> users = new HashSet<>();
}
