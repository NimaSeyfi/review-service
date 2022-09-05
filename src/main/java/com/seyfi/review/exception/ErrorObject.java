package com.seyfi.review.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorObject{
    INTERNAL_ERROR(10400001, "Something went wrong", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(10400002, "Bad Request", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(10400003, "Invalid Input", HttpStatus.BAD_REQUEST),
    PRODUCT_EXIST(10400004, "Product with this productId already exists", HttpStatus.BAD_REQUEST),
    PRODUCT_DOESNT_EXIST(10400005, "Product with this productId does not exist", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(10404001, "Not Found", HttpStatus.NOT_FOUND);

    private Integer errorCode;
    private String errorMessage;
    private HttpStatus status;

    ErrorObject(Integer errorCode, String errorMessage, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

}
