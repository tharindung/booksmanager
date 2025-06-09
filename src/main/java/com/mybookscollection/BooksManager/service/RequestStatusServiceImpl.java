package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.RequestStatusDto;
import com.mybookscollection.BooksManager.entity.RequestStatus;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.RequestStatusRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RequestStatusServiceImpl implements RequestStatusService{

    private RequestStatusRepository requestStatusRepository;

    private ModelMapper modelMapper;

    @Override
    public RequestStatusDto createRequestStatus(RequestStatusDto requestStatusDto) {

        RequestStatus newRequestStatus = modelMapper.map(requestStatusDto, RequestStatus.class);

        RequestStatus savedRequestStatus = requestStatusRepository.save(newRequestStatus);

        return modelMapper.map(savedRequestStatus, RequestStatusDto.class);
    }

    @Override
    public List<RequestStatusDto> getAllRequestStatuses() {

        List<RequestStatus> allRequestStatuses = requestStatusRepository.findAll();

        return allRequestStatuses.stream().map((r)->modelMapper.map(r, RequestStatusDto.class)).collect(Collectors.toList());
    }

    @Override
    public RequestStatusDto getRequestStatusById(Long requestStatusId) {

        //RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status with ID : " + requestStatusId + " does not exist !"));
        RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status", "requestStatusId", requestStatusId));

        return modelMapper.map(foundRequestStatus, RequestStatusDto.class);
    }

    @Override
    public RequestStatusDto updateRequestStatus(Long requestStatusId, RequestStatusDto requestStatusDto) {

        //RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status with ID : " + requestStatusId + " does not exist !"));
        RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status", "requestStatusId", requestStatusId));

        foundRequestStatus.setRequestStatus(requestStatusDto.getRequestStatus());
        foundRequestStatus.setBookRequests(requestStatusDto.getBookRequests());

        RequestStatus updatedRequestStatus = requestStatusRepository.save(foundRequestStatus);

        return modelMapper.map(updatedRequestStatus, RequestStatusDto.class);
    }

    @Override
    public void deleteRequestStatus(Long requestStatusId) {

        //RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status with ID : " + requestStatusId + " does not exist !"));
        RequestStatus foundRequestStatus = requestStatusRepository.findById(requestStatusId).orElseThrow(()->new ResourceNotFoundException("Request Status", "requestStatusId", requestStatusId));

        requestStatusRepository.deleteById(requestStatusId);

    }
}
