package com.seyfi.review.model.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UpdateCommentDto {

    private Boolean isCustomer;

    @Size(min = 1, max = 500, message = "comment content size should fit in 1 to 500 characters")
    private String content;

}
