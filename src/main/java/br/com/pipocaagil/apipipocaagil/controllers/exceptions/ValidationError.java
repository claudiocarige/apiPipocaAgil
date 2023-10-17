package br.com.pipocaagil.apipipocaagil.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private static final long serialVersionUID = 1L;
    private List<FieldMessage> errors = new ArrayList<>();
    public ValidationError() {
        super();
    }
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
