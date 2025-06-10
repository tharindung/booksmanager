package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookRequestDto;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.RequestStatus;
import com.mybookscollection.BooksManager.entity.User;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.BookRequestRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookRequestServiceImpl implements BookRequestService{

    private BookRequestRepository bookRequestRepository;

    private ModelMapper modelMapper;

    @Override
    public BookRequestDto createBookRequest(BookRequestDto bookRequestDto) {

        BookRequest newBookRequest = modelMapper.map(bookRequestDto, BookRequest.class);

        BookRequest savedBookRequest = bookRequestRepository.save(newBookRequest);

        return modelMapper.map(savedBookRequest, BookRequestDto.class);
    }

    @Override
    public List<BookRequestDto> getAllBookRequests() {

        List<BookRequest> allBookRequests = bookRequestRepository.findAll();

        return allBookRequests.stream().map((br)->modelMapper.map(br, BookRequestDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookRequestDto getBookRequestById(Long bookRequestId) {

        //BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book request with ID : " + bookRequestId + " does not exist !"));
        BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book request", "bookRequestId", bookRequestId));

        return modelMapper.map(foundBookRequest, BookRequestDto.class);
    }

    @Override
    public BookRequestDto updateBookRequest(Long bookRequestId, BookRequestDto bookRequestDto) {

        //BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book Request with ID : " + bookRequestId + " does not exist !"));
        BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book Request", "bookRequestId", bookRequestId));

        //foundBookRequest.setBook(bookRequestDto.getBook());
        foundBookRequest.setBook(modelMapper.map(bookRequestDto.getBook(), Book.class));
        //foundBookRequest.setRequestedUser(bookRequestDto.getRequestedUser());
        foundBookRequest.setRequestedUser(modelMapper.map(bookRequestDto.getRequestedUser(), User.class));
        //foundBookRequest.setRequestStatus(bookRequestDto.getRequestStatus());
        foundBookRequest.setRequestStatus(modelMapper.map(bookRequestDto.getRequestStatus(), RequestStatus.class));

        BookRequest updatedBookRequest = bookRequestRepository.save(foundBookRequest);

        return modelMapper.map(updatedBookRequest, BookRequestDto.class);
    }

    @Override
    public void deleteBookRequest(Long bookRequestId) {

        //BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book Request with ID : " + bookRequestId + " does not exist !"));
        BookRequest foundBookRequest = bookRequestRepository.findById(bookRequestId).orElseThrow(()->new ResourceNotFoundException("Book Request", "bookRequestId", bookRequestId));

        bookRequestRepository.deleteById(bookRequestId);
    }
}
