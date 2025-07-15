package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Schema(description = "DTO representing a User in the system")
public class UserDto{

    @Schema(
            description = "Unique identifier of the user",
            example = "101",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long userId;

    @NotEmpty(message = "Name should not be empty !")
    @Schema(
            description = "Full name of the user",
            example = "John Doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userName;

    @NotNull(message = "Country is required !")
    //@JsonManagedReference(value="userCountry")
    @Schema(
            description = "Country associated with the user",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CountryDto userCountry;

    @Email(message = "Email should be valid !")
    @NotEmpty(message = "Email should not be empty !")
    @Schema(
            description = "Email address of the user",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userEmail;

    @NotEmpty(message = "Password should not be empty !")
    @Schema(
            description = "User password (should be encrypted in storage)",
            example = "P@ssw0rd123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userPassword;

    @NotNull(message = "User Joined Date is required !")
    @Schema(
            description = "Date the user joined the system",
            example = "2023-01-15",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "string", //This means although this the Java type is 'LocalDate', in JSON it will appear as a string
            format = "date" //This means that the field follows a 'standard date pattern' (no time part)
    )
    private LocalDate userJoinedDate;

    /* Initial way of using 'book' entity */
    //private Set<Book> books = new HashSet<>();

    /* Alternative way of using 'BookDto' entity - but this will make 'books' fieled available in JSON payload for 'UserDto' but 'bookOwner' fieled unavailable in JSON payload for BookDto */
    //@JsonManagedReference(value="bookOwner")
    //private Set<BookDto> books = new HashSet<>();

    /* Alternative way of using 'BookDto' entity with @JsonIdentityInfo annotation - this will make both 'books' fieled in JSON payload for 'UserDto' as well as'bookOwner' fieled in JSON payload for BookDto available */
    @Schema(
            description = "Books owned by the user",
            implementation = BookDto.class
    )
    private Set<BookDto> books = new HashSet<>();

    //@JsonBackReference(value="reqUser")
    @JsonIgnore
    @Schema(
            description = "Book requests made by the user (ignored in JSON output)",
            hidden = true
    )
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
