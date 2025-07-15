package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
@Schema(description = "DTO representing a book")
public class BookDto {

    @Schema(
            description = "Unique identifier of the book",
            example = "1001",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long bookId;

    @NotEmpty(message = "Book Name should not be empty !")
    @Schema(
            description = "Title of the book",
            example = "Effective Java",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String bookName;

    @Schema(
            description = "Cover image of the book in byte array format (Base64 encoded in JSON)",
            type = "string",
            format = "byte"
    )
    private byte[] bookImg;

    @NotEmpty(message = "Author should not be empty !")
    @Schema(
            description = "Author of the book",
            example = "Joshua Bloch",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String bookAuthor;

    @Schema(
            description = "Date the book was purchased",
            example = "2022-05-20",
            type = "string",
            format = "date"
    )
    private LocalDate bookPurchaseDate;

    @NotNull(message = "Book Category is required !")
    @Schema(
            description = "Category to which the book belongs",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = BookCategoryDto.class
    )
    //@JsonBackReference(value="bookCategory")
    private BookCategoryDto bookCategory;

    @NotNull(message = "Book Condition is required !")
    //@JsonBackReference(value="bookCondition")
    @Schema(
            description = "Condition of the book",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = BookConditionDto.class
    )
    private BookConditionDto bookCondition;

    @Schema(
            description = "Internal catalog number for the book",
            example = "CAT-2023-001"
    )
    private String bookCatalogNo;

    @Schema(
            description = "Additional notes about the book",
            example = "Signed copy by the author"
    )
    private String bookNotes;

    @NotNull(message = "Book Shareable is required !") //Validation only works on Boolean (not boolean)
    //private boolean isBookShareable;
    @Schema(
            description = "Whether the book is available for sharing",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
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
    @Schema(
            description = "User who owns the book",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = UserDisplayDto.class
    )
    private UserDisplayDto bookOwner;

    //@JsonBackReference(value="reqBook")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    @Schema(
            description = "Book requests made for this book (hidden from output)",
            hidden = true
    )
    private Set<BookRequestDto> bookRequests = new HashSet<>();

}
