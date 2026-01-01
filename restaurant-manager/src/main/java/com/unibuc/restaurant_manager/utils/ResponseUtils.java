package com.unibuc.restaurant_manager.utils;

import com.unibuc.restaurant_manager.dto.ErrorStringDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtils {

    private ResponseUtils() {}

    private static <T> ResponseEntity<T> response(T obj, HttpStatus status) {
        return new ResponseEntity<>(obj, status);
    }

    public static <T> ResponseEntity<T> ok(T obj) {
        return response(obj, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created(T obj) {
        return response(obj, HttpStatus.CREATED);
    }

    public static ResponseEntity<Void> noContent() {
        return response(null, HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<ErrorStringDto> badRequest(String err) {
        return response(new ErrorStringDto(err), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ErrorStringDto> badRequest(String template, Object... args) {
        return response(new ErrorStringDto(String.format(template, args)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ErrorStringDto> unauthorized() {
        return response(null, HttpStatus.UNAUTHORIZED);
    }

}
