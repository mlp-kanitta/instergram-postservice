package com.kanittalab.instergram.postservice.exception;

import com.kanittalab.instergram.postservice.constant.CommonConstants;
import com.kanittalab.instergram.postservice.model.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.kanittalab.instergram.postservice.model.CommonResponse;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>  handleValidationExceptions(
            Exception ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ResponseError err = new ResponseError(LocalDateTime.now(), "General error" , details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_GENERAL_ERROR,"General error",err));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc) {

        List<String> details = new ArrayList<String>();
        details.add(exc.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "File Not Found" ,details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_FILE_EXCEED,err.getMessage(),err));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {

        List<String> details = new ArrayList<String>();
        details.add(exc.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "File Size Exceeded" ,details);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_FILE_EXCEED,err.getMessage(),err));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ResponseError err = new ResponseError(LocalDateTime.now(), "validationException" ,ex.getBindingResult().getAllErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_VALIDATION_ERROR,"Validation error",err));
    }

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public ResponseEntity<Object> validationException(ValidationException exc) {

        List<String> details = new ArrayList<String>();
        details.add(exc.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "Validation Exception" ,details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_VALIDATION_ERROR,err.getMessage(),err));
    }
}
