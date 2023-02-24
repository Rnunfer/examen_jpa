package org.iesvdm.examen.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) { super("Not found Product with id: " + id); }
}
