package co.uk.timetravel.errors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.ImmutableList;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiErrorHandler {

    private static final String VALIDATION_ERROR = "Validation Error";
    private static final String INVALID_INPUTS = "Invalid Inputs";
    private static final String PARADOX_ERROR = "Paradox Error";
    private static final String INVALID_DATE = "Invalid Date";
    private static final String INVALID_DATE_FORMAT = "Invalid Date Format";
    private static final String PARADOX_MESSAGE = "choose another date, you have been here before in the same day";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final BindingResult result = ex.getBindingResult();
        final ApiError apiError = ApiError.builder()
                .errorCode(VALIDATION_ERROR)
                .errorMessage(INVALID_INPUTS)
                .errors(getDefaultMessageList(result)).build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private List<String> getDefaultMessageList(BindingResult result) {
        return result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity handleInvalidFormatException() {
        final ApiError apiError = ApiError.builder()
                .errorCode(VALIDATION_ERROR)
                .errorMessage(INVALID_DATE)
                .errors(ImmutableList.of(INVALID_DATE_FORMAT)).build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParadoxException.class)
    public ResponseEntity handleParadoxException() {
        final ApiError apiError = ApiError.builder()
                .errorCode(PARADOX_ERROR)
                .errorMessage(INVALID_DATE)
                .errors(ImmutableList.of(PARADOX_MESSAGE)).build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}

