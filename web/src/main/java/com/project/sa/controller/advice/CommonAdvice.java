package com.project.sa.controller.advice;

import com.project.sa.controller.advice.error.ErrorCode;
import com.project.sa.controller.advice.error.ErrorFieldValidationInfo;
import com.project.sa.controller.advice.error.ErrorResponse;
import com.project.sa.exception.IllegalParameterException;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.util.ErrorMessageReader;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import static com.project.sa.controller.advice.error.ErrorCode.RESOURCE_NOT_FOUND;
import static com.project.sa.controller.advice.error.ErrorCode.RESOURCE_ALREADY_EXIST;

@ControllerAdvice
@Log4j2
public class CommonAdvice {
    private final MessageSource messageSource;

    @Autowired
    public CommonAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        String errorMessage;
        if (ex.getNameResource() != null) {
            errorMessage = String.format(messageSource.getMessage(ex.getMessage(), new Object[]{},
                    locale), ex.getNameResource(), ex.getIdResource());
        } else {
            errorMessage = String.format(messageSource.getMessage(ex.getMessage(), new Object[]{},
                    locale), ex.getIdResource());
        }
        ErrorResponse errorResponse = new ErrorResponse(RESOURCE_NOT_FOUND, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceIsAlreadyExistException(ResourceAlreadyExistsException ex, Locale locale) {
        String errorMessage;
        errorMessage = String.format(messageSource.getMessage(ex.getMessage(), new Object[]{},
                locale), ex.getNameResource());
        ErrorResponse errorResponse = new ErrorResponse(RESOURCE_ALREADY_EXIST, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalParameterException.class)
    public ResponseEntity<ErrorResponse> handleIllegalParameterException(IllegalParameterException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE, new Object[]{},
                locale);
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        List<ErrorFieldValidationInfo> fields = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        for (FieldError field : result.getFieldErrors()) {
            ErrorFieldValidationInfo errorInfo = new ErrorFieldValidationInfo();
            errorInfo.setFieldName(field.getField());
            errorInfo.setErrorCode(field.getCode());
            errorInfo.setRejectedValue(field.getRejectedValue());
            if (field.getDefaultMessage() != null) {
                errorInfo.setErrorMessage(messageSource.getMessage(field.getDefaultMessage(), new Object[]{},
                        locale));
            }
            fields.add(errorInfo);
        }
        response.setErrorFieldsValidationInfo(fields);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INTERNAL_SERVER_ERROR,
                new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJHttpMessageNotReadableException(HttpMessageNotReadableException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE,
                new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE,
                new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        List<ErrorFieldValidationInfo> fields = new ArrayList<>();
        Set<ConstraintViolation<?>> result = ex.getConstraintViolations();
        result.forEach(s -> fields.add(new ErrorFieldValidationInfo(findLastElementName(s.getPropertyPath()),
                s.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
                s.getInvalidValue(), messageSource.getMessage(s.getMessage(),
                new Object[]{}, locale))));
        response.setErrorFieldsValidationInfo(fields);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindingException(BindException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE,
                new Object[]{}, locale);
        List<ErrorFieldValidationInfo> errorFields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(f -> errorFields.add(new ErrorFieldValidationInfo(f.getField(),
                        f.getCode(), f.getRejectedValue(), messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE,
                        new Object[]{}, locale))));
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage, errorFields);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private static String findLastElementName(Path path) {
        String fieldName = null;
        Iterator<Path.Node> iterator = path.iterator();
        while (iterator.hasNext()) {
            fieldName = iterator.next().getName();
        }
        return fieldName;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_REQUEST, new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST_PATH, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.METHOD_NOT_ALLOWED, new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(MissingServletRequestParameterException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.BAD_REQUEST_PARAM_ABSENT, new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST_PARAM_ABSENT, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE, new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        log.log(Level.ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}