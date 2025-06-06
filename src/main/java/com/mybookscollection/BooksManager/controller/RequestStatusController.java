package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.RequestStatusDto;
import com.mybookscollection.BooksManager.service.RequestStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/request-statuses")
public class RequestStatusController {

    private RequestStatusService requestStatusService;

    @PostMapping
    public ResponseEntity<RequestStatusDto> createRequestStatus(@RequestBody RequestStatusDto requestStatusDto){

        RequestStatusDto savedRequestStatus = requestStatusService.createRequestStatus(requestStatusDto);

        return new ResponseEntity<>(savedRequestStatus, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RequestStatusDto>> getAllRequestStatuses()
    {
        List<RequestStatusDto> allRequestStatuses = requestStatusService.getAllRequestStatuses();

        return new ResponseEntity<>(allRequestStatuses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RequestStatusDto> getRequsetStatusById(@PathVariable("id") Long requestStatusId)
    {
        RequestStatusDto foundRequestStatus = requestStatusService.getRequestStatusById(requestStatusId);

        return new ResponseEntity<>(foundRequestStatus, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RequestStatusDto> updateRequestStatus(@PathVariable("id") Long requestStatusId, @RequestBody RequestStatusDto requestStatusDto)
    {
        RequestStatusDto updatedRequestStatus = requestStatusService.updateRequestStatus(requestStatusId, requestStatusDto);

        return new ResponseEntity<>(updatedRequestStatus, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRequestStatus(@PathVariable("id") Long requestStatusId)
    {
        requestStatusService.deleteRequestStatus(requestStatusId);

        return new ResponseEntity<>("Request Status with ID : "+requestStatusId+" deleted successfully !", HttpStatus.OK);
    }
}
