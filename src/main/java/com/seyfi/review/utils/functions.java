package com.seyfi.review.utils;

import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.CreateVoteDto;

public class functions {

    public static void check_product_is_commentable(ProductDetail productDetail, CreateCommentDto createCommentDto){
        if (!productDetail.getIsVisible()){
            throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);
        }
        if (!productDetail.getIsCommentable()){
            throw new ApiError(ErrorObject.PRODUCT_IS_NOT_COMMENTABLE);
        }
        if (!productDetail.getIsPublic() && !createCommentDto.getIsCustomer()){
            throw new ApiError(ErrorObject.PRODUCT_ONLY_CUSTOMER_CAN_COMMENT);
        }
    }

    public static void check_product_is_votable(ProductDetail productDetail, CreateVoteDto createVoteDto){
        if (!productDetail.getIsVisible()){
            throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);
        }
        if (!productDetail.getIsVotable()){
            throw new ApiError(ErrorObject.PRODUCT_IS_NOT_VOTABLE);
        }
        if (!productDetail.getIsPublic() && !createVoteDto.getIsCustomer()){
            throw new ApiError(ErrorObject.PRODUCT_ONLY_CUSTOMER_CAN_VOTE);
        }
    }

}
