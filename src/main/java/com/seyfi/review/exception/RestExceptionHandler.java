package com.seyfi.review.exception;

import com.seyfi.review.model.response.GeneralResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.HashMap;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(ApiError.class)
   protected ResponseEntity<GeneralResponse> handleApiError(ApiError error) {
       GeneralResponse generalResponse = new GeneralResponse();
       generalResponse.setError(true);
       generalResponse.setResult_code(error.getError().getErrorCode());
       generalResponse.setMessage(error.getError().getErrorMessage());
       ResponseEntity<GeneralResponse> responseEntity
               = new ResponseEntity<GeneralResponse>(generalResponse, error.getError().getStatus());
       return responseEntity;
   }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<GeneralResponse> handleValidationError(ValidationException error) {
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.INVALID_INPUT.getErrorCode());
        HashMap<String, String> errors = new HashMap<>();
        generalResponse.setMessage(error.getLocalizedMessage());
        ResponseEntity<GeneralResponse> responseEntity
                = new ResponseEntity<GeneralResponse>(generalResponse, ErrorObject.INVALID_INPUT.getStatus());
        return responseEntity;
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.INVALID_INPUT.getErrorCode());

        HashMap<Integer, String> error_dic = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        for(FieldError error : bindingResult.getFieldErrors()) {
            Integer field_code = FieldCode.get_code_by_name(error.getField());
            error_dic.put(field_code, error.getDefaultMessage());
        }
        generalResponse.setMessage(error_dic);
        return new ResponseEntity<Object>(generalResponse, ErrorObject.INVALID_INPUT.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.BAD_REQUEST.getErrorCode());
        generalResponse.setMessage(ErrorObject.BAD_REQUEST.getErrorMessage());
        ResponseEntity<Object> responseEntity
                = new ResponseEntity<Object>(generalResponse, ErrorObject.BAD_REQUEST.getStatus());
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request){
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.INVALID_TYPE.getErrorCode());
        String field_name;
        try {
            Field field = ex.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field_name = (String) field.get(ex);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            field_name = "unknown";
        }
        Integer fieldCode = FieldCode.get_code_by_name(field_name);
        String message = field_name + " type is not correct. it must be : "+ex.getRequiredType();
        HashMap<Integer, String> error = new HashMap<Integer, String>();
        error.put(fieldCode, message);
        generalResponse.setMessage(error);
        ResponseEntity<Object> responseEntity
                = new ResponseEntity<Object>(generalResponse, ErrorObject.INVALID_TYPE.getStatus());
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                  HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request){
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.METHOD_NOT_ALLOWED.getErrorCode());
        generalResponse.setMessage(ErrorObject.METHOD_NOT_ALLOWED.getErrorMessage());
        ResponseEntity<Object> responseEntity
                = new ResponseEntity<Object>(generalResponse, ErrorObject.METHOD_NOT_ALLOWED.getStatus());
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request){
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_code(ErrorObject.MISSING_PARAMETER.getErrorCode());
        String field_name = ex.getParameterName();
        Integer fieldCode = FieldCode.get_code_by_name(field_name);
        String message = field_name + " parameter is missing";
        HashMap<Integer, String> error = new HashMap<Integer, String>();
        error.put(fieldCode, message);
        generalResponse.setMessage(error);
        ResponseEntity<Object> responseEntity
                = new ResponseEntity<Object>(generalResponse, ErrorObject.MISSING_PARAMETER.getStatus());
        return responseEntity;
    }
}
