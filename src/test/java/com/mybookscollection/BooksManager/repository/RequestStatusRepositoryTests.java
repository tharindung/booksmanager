package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.RequestStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RequestStatusRepositoryTests {

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    private RequestStatus requestStatus;

    @BeforeEach
    public void setup()
    {
        requestStatus = RequestStatus.builder().requestStatus("Pending").build();
    }

    //Junit test for create request status operation
    @DisplayName("Junit test for create request status operation")
    @Test
    public void givenRequestStatusObject_whenSave_thenReturnSavedRequestStatusObject()
    {
        //Given - precondition or setup

        //When - action or the behavior we're testing
        RequestStatus savedRequestStatus = requestStatusRepository.save(requestStatus);

        //Then - verify the output
        Assertions.assertThat(savedRequestStatus).isNotNull();
        Assertions.assertThat(savedRequestStatus.getRequestStatusId()).isGreaterThan(0);
    }

    //Junit test for get all request statuses
    @DisplayName("Junit test for get all request statuses")
    @Test
    public void givenRequestStatusesList_whenFindAll_thenReturnRequestStatusesList()
    {
        //Given - precondition or setup
        requestStatusRepository.save(requestStatus);

        RequestStatus requestStatus1 = RequestStatus.builder().requestStatus("Approved").build();

        requestStatusRepository.save(requestStatus1);

        //When - action or the behavior we're testing
        List<RequestStatus> allRequestStatuses = requestStatusRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(allRequestStatuses).isNotNull();
        Assertions.assertThat(allRequestStatuses.size()).isEqualTo(2);
    }

    //Junit test for get request status by id
    @DisplayName("Junit test for get request status by id")
    @Test
    public void givenRequestStatusObject_whenFindById_thenReturnRequestStatusObject()
    {
        //Given - precondition or setup
        requestStatusRepository.save(requestStatus);

        //When - action or the behavior we're testing
        RequestStatus returnedRequestStatus = requestStatusRepository.findById(requestStatus.getRequestStatusId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedRequestStatus).isNotNull();
    }

    //Junit test for update request status
    @DisplayName("Junit test for update request status")
    @Test
    public void givenRequestStatusObject_whenUpdateRequestStatus_thenReturnUpdatedRequestStatus()
    {
        //Given - precondition or setup
        requestStatusRepository.save(requestStatus);

        //When - action or behavior we're testing
        RequestStatus savedRequestStatus = requestStatusRepository.findById(requestStatus.getRequestStatusId()).get();
        savedRequestStatus.setRequestStatus("Denied");

        RequestStatus updatedRequestStatus = requestStatusRepository.save(savedRequestStatus);

        //Then - verify the output
        Assertions.assertThat(updatedRequestStatus.getRequestStatus()).isEqualTo("Denied");
    }

    //Junit test for delete request status
    @DisplayName("Junit test for delete request status")
    @Test
    public void givenRequestStatusObject_whenDeleteById_thenRemoveRequestStatusObject()
    {
        //Given - precondition or setup
        requestStatusRepository.save(requestStatus);

        //When - action or the behavior we're testing
        requestStatusRepository.deleteById(requestStatus.getRequestStatusId());

        Optional<RequestStatus> optionalRequestStatus = requestStatusRepository.findById(requestStatus.getRequestStatusId());

        //Then - verify the output
        Assertions.assertThat(optionalRequestStatus).isEmpty();
    }
}
