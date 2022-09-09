package com.seyfi.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class CreateCommentDto {


    @NotNull(message = "productId can't be null")
    @Positive(message = "productId should be a positive number")
    private Long productId;

    @NotNull(message = "userId can't be null")
    @Positive(message = "userId should be a positive number")
    private Long userId;

    @NotNull(message = "isCustomer can't be null")
    private Boolean isCustomer;

    @NotNull(message = "message content can't be null")
    @NotBlank(message = "content can't be blank")
    @NotEmpty(message = "content can't be empty")
    @Size(min = 1, max = 500, message = "comment content size should fit in 1 to 500 characters")
    private String content;

}
