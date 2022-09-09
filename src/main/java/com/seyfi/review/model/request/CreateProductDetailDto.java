package com.seyfi.review.model.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateProductDetailDto {

    @NotNull(message = "productId can't be null")
    @Positive(message = "productId should be a positive number")
    private Integer productId;

    private Boolean isVisible = true;

    private Boolean isCommentable = true;

    private Boolean isVotable = true;

    private Boolean isPublic = true;

}
