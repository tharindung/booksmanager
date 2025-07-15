package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.RequestStatusDto;
import com.mybookscollection.BooksManager.service.RequestStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/request-statuses")
@Tag(name = "Request Statuses", description = "CRUD operations for request statuses")
public class RequestStatusController {

    private RequestStatusService requestStatusService;

    @Operation(
            summary = "Create a new request status",
            description = "Creates a new request status and returns the saved request status",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Request status created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RequestStatusDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<RequestStatusDto> createRequestStatus(@RequestBody @Valid RequestStatusDto requestStatusDto){

        RequestStatusDto savedRequestStatus = requestStatusService.createRequestStatus(requestStatusDto);

        return new ResponseEntity<>(savedRequestStatus, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all request statuses",
            description = "Returns a list of all request statuses",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of request statuses",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RequestStatusDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<RequestStatusDto>> getAllRequestStatuses()
    {
        List<RequestStatusDto> allRequestStatuses = requestStatusService.getAllRequestStatuses();

        return new ResponseEntity<>(allRequestStatuses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a request status by ID",
            description = "Returns a single request status by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request status found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RequestStatusDto.class))),
                    @ApiResponse(responseCode = "404", description = "Request status not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<RequestStatusDto> getRequsetStatusById(@PathVariable("id") Long requestStatusId)
    {
        RequestStatusDto foundRequestStatus = requestStatusService.getRequestStatusById(requestStatusId);

        return new ResponseEntity<>(foundRequestStatus, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a request status",
            description = "Updates an existing request status by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request status updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RequestStatusDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Request status not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<RequestStatusDto> updateRequestStatus(@PathVariable("id") Long requestStatusId, @RequestBody @Valid RequestStatusDto requestStatusDto)
    {
        RequestStatusDto updatedRequestStatus = requestStatusService.updateRequestStatus(requestStatusId, requestStatusDto);

        return new ResponseEntity<>(updatedRequestStatus, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a request status",
            description = "Deletes a request status by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request status deleted"),
                    @ApiResponse(responseCode = "404", description = "Request status not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRequestStatus(@PathVariable("id") Long requestStatusId)
    {
        requestStatusService.deleteRequestStatus(requestStatusId);

        return new ResponseEntity<>("Request Status with ID : "+requestStatusId+" deleted successfully !", HttpStatus.OK);
    }
}
