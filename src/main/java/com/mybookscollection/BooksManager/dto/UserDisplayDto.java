package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO representing a user without sensitive data")
public class UserDisplayDto {

    @Schema(
            description = "Unique identifier of the user",
            example = "101",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long userId;

    @Schema(
            description = "Full name of the user",
            example = "John Doe"
    )
    private String userName;

    //@JsonManagedReference(value="userCountry")
    @Schema(
            description = "Country associated with the user"
    )
    private CountryDto userCountry;

    @Schema(
            description = "Email address of the user",
            example = "john.doe@example.com"
    )
    private String userEmail;

    @Schema(
            description = "Date the user joined the system",
            example = "2023-01-15",
            type = "string",
            format = "date"
    )
    private LocalDate userJoinedDate;

    @Schema(
            description = "Books owned by the user",
            implementation = BookDto.class
    )
    private Set<BookDto> books = new HashSet<>();

    //@JsonBackReference(value="reqUser")
    @JsonIgnore
    @Schema(
            description = "Book requests made by the user (hidden from JSON output)",
            hidden = true
    )
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
