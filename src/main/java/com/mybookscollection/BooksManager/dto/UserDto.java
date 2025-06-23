package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.Country;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserDto{

    private Long userId;

    @NotEmpty(message = "Name should not be empty !")
    private String userName;

    @NotNull(message = "Country is required !")
    //@JsonManagedReference(value="userCountry")
    private CountryDto userCountry;

    @Email(message = "Email should be valid !")
    @NotEmpty(message = "Email should not be empty !")
    private String userEmail;

    @NotEmpty(message = "Password should not be empty !")
    private String userPassword;

    @NotNull(message = "User Joined Date is required !")
    private LocalDate userJoinedDate;

    /* Initial way of using 'book' entity */
    //private Set<Book> books = new HashSet<>();

    /* Alternative way of using 'BookDto' entity - but this will make 'books' fieled available in JSON payload for 'UserDto' but 'bookOwner' fieled unavailable in JSON payload for BookDto */
    //@JsonManagedReference(value="bookOwner")
    //private Set<BookDto> books = new HashSet<>();

    /* Alternative way of using 'BookDto' entity with @JsonIdentityInfo annotation - this will make both 'books' fieled in JSON payload for 'UserDto' as well as'bookOwner' fieled in JSON payload for BookDto available */
    private Set<BookDto> books = new HashSet<>();

    //@JsonBackReference(value="reqUser")
    @JsonIgnore
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
