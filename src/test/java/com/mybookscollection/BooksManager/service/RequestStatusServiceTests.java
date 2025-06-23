package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.RequestStatusDto;
import com.mybookscollection.BooksManager.entity.RequestStatus;
import com.mybookscollection.BooksManager.repository.RequestStatusRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestStatusServiceTests {

    @Mock
    private RequestStatusRepository requestStatusRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RequestStatusServiceImpl requestStatusService;

    private RequestStatus requestStatus;

    private RequestStatusDto requestStatusDto;

    @BeforeEach
    public void setup()
    {
        requestStatus = RequestStatus.builder().
                            requestStatusId(1L).
                            requestStatus("Pending").build();

        requestStatusDto = RequestStatusDto.builder().
                            requestStatusId(1L).
                            requestStatus("Pending").build();
    }

    //Junit test for createRequestStatus method
    @DisplayName("Junit test for createRequestStatus method")
    @Test
    public void givenRequestStatusDtoObject_whenCreateRequestStatus_thenReturnSavedRequestStatusDtoObject(){

        //given - precondition or setup
        given(requestStatusRepository.save(requestStatus)).willReturn(requestStatus);

        doReturn(requestStatusDto).when(modelMapper).map(requestStatus, RequestStatusDto.class);
        doReturn(requestStatus).when(modelMapper).map(requestStatusDto, RequestStatus.class);

        //when - action or the behavior we're testing
        RequestStatusDto savedRequestStatus = requestStatusService.createRequestStatus(requestStatusDto);

        //then - verify the output
        Assertions.assertThat(savedRequestStatus).isNotNull();
    }

    //Junit test for getAllRequestStatuses method positive scenario
    @DisplayName("Junit test for getAllRequestStatuses method positive scenario")
    @Test
    public void givenRequestStausList_whenGetAllRequestStatuses_thenReturnRequestStatusDtoList(){

        //given - precondition or setup
        RequestStatus requestStatus1 = RequestStatus.builder().
                                        requestStatusId(2L).
                                        requestStatus("Approved").build();

        given(requestStatusRepository.findAll()).willReturn(List.of(requestStatus, requestStatus1));

        //when - action or the behavior we're testing
        List<RequestStatusDto> allRequestStatuses = requestStatusService.getAllRequestStatuses();

        //then - verify the output
        Assertions.assertThat(allRequestStatuses).isNotNull();
        Assertions.assertThat(allRequestStatuses.size()).isEqualTo(2);

    }

    //Junit test for getAllRequestStatuses method negative scenario
    @DisplayName("Junit test for getAllRequestStatuses method negative scenario")
    @Test
    public void givenEmptyRequestStatusList_whenGetAllRequestStatuses_thenReturnEmptyRequestStatusDtoList(){

        //given - precondition or setup
        given(requestStatusRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<RequestStatusDto> allRequestStatuses = requestStatusService.getAllRequestStatuses();

        //then - verify the output
        Assertions.assertThat(allRequestStatuses).isEmpty();
        Assertions.assertThat(allRequestStatuses.size()).isEqualTo(0);
    }

    //Junit test for getRequestStatusById method
    @DisplayName("Junit test for getRequestStatusById method")
    @Test
    public void givenRequestStatusId_whenGetRequestStatusById_thenReturnRequestStatusDtoObject(){

        //given - precondition or setup
        given(requestStatusRepository.findById(requestStatus.getRequestStatusId())).willReturn(Optional.of(requestStatus));

        doReturn(requestStatusDto).when(modelMapper).map(requestStatus, RequestStatusDto.class);

        //when - action or the behavior we're testing
        RequestStatusDto returnedRequestStatus = requestStatusService.getRequestStatusById(requestStatus.getRequestStatusId());

        //then - verify the output
        Assertions.assertThat(returnedRequestStatus).isNotNull();
    }

    //Junit test for updateRequestStatus method
    @DisplayName("Junit test for updateRequestStatus method")
    @Test
    public void givenRequestStausDto_whenUpdateRequestStatus_thenReturnUpdatedRequestStatusDto(){

        //given - precondition or setup
        given(requestStatusRepository.findById(requestStatus.getRequestStatusId())).willReturn(Optional.of(requestStatus));

        given(requestStatusRepository.save(requestStatus)).willReturn(requestStatus);

        doReturn(requestStatusDto).when(modelMapper).map(requestStatus, RequestStatusDto.class);

        requestStatus.setRequestStatus("Denied");

        requestStatusDto.setRequestStatus("Denied");

        //when - action or the behavior we're testing
        RequestStatusDto updatedRequestStatus = requestStatusService.updateRequestStatus(requestStatusDto.getRequestStatusId(), requestStatusDto);

        //then - verify the output
        Assertions.assertThat(updatedRequestStatus.getRequestStatus()).isEqualTo("Denied");
    }

    //Junit test for deleteRequestStatus method
    @DisplayName("Junit test for deleteRequestStatus method")
    @Test
    public void givenRequestStatusId_whenDeleteRequestStatus_thenReturnNothing(){

        //given - precondition or setup
        given(requestStatusRepository.findById(requestStatus.getRequestStatusId())).willReturn(Optional.of(requestStatus));

        willDoNothing().given(requestStatusRepository).deleteById(requestStatus.getRequestStatusId());

        //when - action or the behavior we're testing
        requestStatusService.deleteRequestStatus(requestStatusDto.getRequestStatusId());

        //then - verify the output
        verify(requestStatusRepository, times(1)).deleteById(requestStatusDto.getRequestStatusId());
    }
}
