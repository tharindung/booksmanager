package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.BookRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusDto {

    private Long requestStatusId;

    @NotEmpty(message = "Request Status should not be empty !")
    private String requestStatus;

    private Set<BookRequest> bookRequests = new HashSet<>();
}
