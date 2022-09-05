package com.seyfi.review.exception;
import com.seyfi.review.model.response.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ValidationException;
import java.util.HashMap;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(ApiError.class)
   protected ResponseEntity<GeneralResponse> handleApiError(ApiError error) {
       GeneralResponse generalResponse = new GeneralResponse();
       generalResponse.setError(true);
       generalResponse.setResult_number(error.getError().getErrorCode());
       generalResponse.setMessage(error.getError().getErrorMessage());
       ResponseEntity<GeneralResponse> responseEntity
               = new ResponseEntity<GeneralResponse>(generalResponse, error.getError().getStatus());
       return responseEntity;
   }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<GeneralResponse> handleValidationError(ValidationException error) {
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setError(true);
        generalResponse.setResult_number(ErrorObject.INVALID_INPUT.getErrorCode());
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
        generalResponse.setResult_number(ErrorObject.INVALID_INPUT.getErrorCode());

        HashMap<String, String> error_dic = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        for(FieldError error : bindingResult.getFieldErrors()) {
            error_dic.put(error.getField(), error.getDefaultMessage());
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
        generalResponse.setResult_number(ErrorObject.BAD_REQUEST.getErrorCode());
        generalResponse.setMessage(ErrorObject.BAD_REQUEST.getErrorMessage());
        ResponseEntity<Object> responseEntity
                = new ResponseEntity<Object>(generalResponse, ErrorObject.BAD_REQUEST.getStatus());
        return responseEntity;
    }


}
