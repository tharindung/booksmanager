package com.mybookscollection.BooksManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a book request")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookRequestId")
public class BookRequestDto {

    @Schema(
            description = "Unique identifier for the book request",
            example = "5001",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long bookRequestId;

    @NotNull(message = "Book is required !")
    @Schema(
            description = "The book being requested",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = BookDto.class
    )
    //@JsonManagedReference(value="reqBook")
    private BookDto book;

    //@NotNull(message = "Requested User is required !")
    //@JsonManagedReference(value="reqUser")
    //private UserDto requestedUser;

    /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    @NotNull(message = "Requested User is required !")
    @Schema(
            description = "User who made the book request",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = UserDisplayDto.class
    )
    //@JsonManagedReference(value="reqUser")
    private UserDisplayDto requestedUser;

    @NotNull(message = "Request Status is required !")
    @Schema(
            description = "Status of the book request",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = RequestStatusDto.class
    )
    //@JsonManagedReference(value="reqStatus")
    private RequestStatusDto requestStatus;
}
