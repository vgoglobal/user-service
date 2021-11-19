package de.exchange.exception;

import de.exchange.model.ApiErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiErrorResponse(400, 4041, ex.getMessage()), headers, status);

    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
        return new ApiErrorResponse(400, 5001, ex.getMessage());
    }

    @ExceptionHandler(value = {HttpClientErrorException.Unauthorized.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse unauthorizedException(ConstraintViolationException ex) {
        return new ApiErrorResponse(401, 5002, ex.getMessage());
    }

    @ExceptionHandler(value = {NoContentException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiErrorResponse noContentException(NoContentException ex) {
        return new ApiErrorResponse(204, 5003, "Timestamp expired");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse unknownException(Exception ex) {
        return new ApiErrorResponse(500, 5004, ex.getMessage());
    }

    @ExceptionHandler(value = {CustomDBException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse customDBException(Exception ex) {
        return new ApiErrorResponse(500, 5005, ex.getMessage());
    }


}