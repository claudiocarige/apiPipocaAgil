package br.com.pipocaagil.apipipocaagil.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatDate {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static LocalDateTime formatStringToLocalDateTime(String data){
        try {
            return LocalDateTime.parse(data, formatter);
        }catch (DateTimeParseException e) {
            throw new RuntimeException("Error converting String to LocalDateTime. ", e);
        }
    }
    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        return formatter.format(dateTime);
    }
}
