package com.seyfi.review.model.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateProductDetailDto {

    @NotNull(message = "productId can't be null")
    @Column(name = "product_id", nullable = false)
    @Positive(message = "productId should be a positive number")
    private Integer productId;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = true;

    @Column(name = "is_commentable", nullable = false)
    private Boolean isCommentable = true;

    @Column(name = "is_votable", nullable = false)
    private Boolean isVotable = true;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

}
