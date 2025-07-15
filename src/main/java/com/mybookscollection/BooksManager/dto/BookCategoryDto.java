package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a Book Category") //Describing the DTO class for Swagger
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookCategoryId")
public class BookCategoryDto {

    @Schema(description = "Unique identifier of the book category", example = "1", accessMode = Schema.AccessMode.READ_ONLY) //Describing fields for Swagger - READ_ONLY specifies that this field should only appear in responses (not in POST/PUT requests)
    private Long bookCategoryId;

    @NotEmpty(message = "Book Category should not be empty !")
    @Schema(description = "Name of the book category", example = "History", requiredMode = Schema.RequiredMode.REQUIRED) //requiredMode here specifies that filed should present in Request body
    private String bookCategory;

    //@JsonManagedReference(value="bookCategory")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    @Schema(description = "Set of books under this category (ignored in JSON)", hidden = true) //hidden=true specifies Swagger to exclude this filed
    private Set<BookDto> books = new HashSet<>();
}
