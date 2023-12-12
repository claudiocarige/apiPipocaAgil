package br.com.pipocaagil.apipipocaagil.services.impl;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatDate {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static OffsetDateTime formatStringToLocalDateTime(String data){
        try {
            return OffsetDateTime.parse(data, formatter);
        }catch (DateTimeParseException e) {
            throw new RuntimeException("Error converting String to OffsetDateTime. ", e);
        }
    }
}
