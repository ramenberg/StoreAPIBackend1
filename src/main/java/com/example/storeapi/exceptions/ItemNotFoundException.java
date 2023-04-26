package com.example.storeapi.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Could not find item with the id " + id + ".");
    }
}
