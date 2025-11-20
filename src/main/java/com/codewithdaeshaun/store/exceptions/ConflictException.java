package com.codewithdaeshaun.store.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}