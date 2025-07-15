package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.BookRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a status for a book request")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "requestStatusId")
public class RequestStatusDto {

    @Schema(
            description = "Unique identifier for the request status",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long requestStatusId;

    @NotEmpty(message = "Request Status should not be empty !")
    @Schema(
            description = "Name of the request status",
            example = "Pending",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String requestStatus;

    //@JsonBackReference(value="reqStatus")
    @JsonIgnore//To reduce the complexity of the Json payload we can ignore this
    @Schema(
            description = "List of book requests associated with this status (ignored in JSON)",
            hidden = true
    )
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
