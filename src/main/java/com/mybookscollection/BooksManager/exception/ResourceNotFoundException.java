package com.mybookscollection.BooksManager.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }
}
