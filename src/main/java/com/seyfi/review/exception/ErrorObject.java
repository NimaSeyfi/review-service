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
    PRODUCT_INVISIBLE(10400006, "Product is invisible", HttpStatus.BAD_REQUEST),
    PRODUCT_IS_NOT_COMMENTABLE(10400007, "Commenting is off for this Product", HttpStatus.BAD_REQUEST),
    PRODUCT_IS_NOT_VOTABLE(10400008, "Voting is off for this Product", HttpStatus.BAD_REQUEST),
    PRODUCT_ONLY_CUSTOMER_CAN_COMMENT(10400009, "Only customers of this product can comment", HttpStatus.BAD_REQUEST),
    PRODUCT_ONLY_CUSTOMER_CAN_VOTE(10400010, "Only customers of this product can vote", HttpStatus.BAD_REQUEST),
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
