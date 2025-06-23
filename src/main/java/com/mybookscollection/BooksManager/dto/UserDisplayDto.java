package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserDisplayDto {

    private Long userId;

    private String userName;

    //@JsonManagedReference(value="userCountry")
    private CountryDto userCountry;

    private String userEmail;

    private LocalDate userJoinedDate;

    private Set<BookDto> books = new HashSet<>();

    //@JsonBackReference(value="reqUser")
    @JsonIgnore
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
