package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.RequestStatusDto;

import java.util.List;

public interface RequestStatusService {

    RequestStatusDto createRequestStatus(RequestStatusDto requestStatusDto);

    List<RequestStatusDto> getAllRequestStatuses();

    RequestStatusDto getRequestStatusById(Long requestStatusId);

    RequestStatusDto updateRequestStatus(Long requestStatusId, RequestStatusDto requestStatusDto);

    void deleteRequestStatus(Long requestStatusId);
}
