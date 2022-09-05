package com.seyfi.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError extends RuntimeException{

    private ErrorObject error;

}
