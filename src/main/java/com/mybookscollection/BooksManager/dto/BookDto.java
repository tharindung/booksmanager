package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.BookCategory;
import com.mybookscollection.BooksManager.entity.BookCondition;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class BookDto {

    private Long bookId;

    @NotEmpty(message = "Book Name should not be empty !")
    private String bookName;

    private byte[] bookImg;

    @NotEmpty(message = "Author should not be empty !")
    private String bookAuthor;

    private LocalDate bookPurchaseDate;

    @NotNull(message = "Book Category is required !")
    //@JsonBackReference(value="bookCategory")
    private BookCategoryDto bookCategory;

    @NotNull(message = "Book Condition is required !")
    //@JsonBackReference(value="bookCondition")
    private BookConditionDto bookCondition;

    private String bookCatalogNo;

    private String bookNotes;

    @NotNull(message = "Book Shareable is required !") //Validation only works on Boolean (not boolean)
    //private boolean isBookShareable;
    private Boolean bookShareable;

    /* Initial way of using 'User' entity */
    //private User bookOwner;

    /* Alternative way of using 'UserDto' entity - Due to '@JsonBackReference', this will make 'bookOwner' fieled unavailable in JSON payload for BookDto but 'books' fieled available in JSON payload for 'UserDto' */
    //@JsonBackReference(value="bookOwner")
    /* Alternative way of using 'BookDto' entity with @JsonIdentityInfo annotation - this will make both 'bookOwner' field in JSON payload for BookDto as well as 'books' fieled in JSON payload for 'UserDto' available */
    //@NotNull(message = "Book Owner is required !")
    //private UserDto bookOwner;

    /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    @NotNull(message = "Book Owner is required !")
    private UserDisplayDto bookOwner;

    //@JsonBackReference(value="reqBook")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    private Set<BookRequestDto> bookRequests = new HashSet<>();

}
