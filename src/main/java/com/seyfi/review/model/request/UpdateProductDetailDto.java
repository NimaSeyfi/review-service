package com.seyfi.review.model.request;

import lombok.Getter;

@Getter
public class UpdateProductDetailDto {

    private Boolean isVisible;

    private Boolean isCommentable;

    private Boolean isVotable;

    private Boolean isPublic;

}
