package br.com.pipocaagil.apipipocaagil.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
    }
    public List<FieldMessage> getErrors() {
        return errors;
    }
    public void addError(String fieldname, String message) {
        this.errors.add(new FieldMessage(fieldname, message));
    }
}
