package com.seyfi.review.model.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class UpdateProductDetailDto {

    private Boolean isVisible;

    private Boolean isCommentable;

    private Boolean isVotable;

    private Boolean isPublic;

}
