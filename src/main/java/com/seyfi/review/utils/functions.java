package com.seyfi.review.utils;

import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.CreateVoteDto;

public class functions {

    public static void check_product_is_commentable(Product product, CreateCommentDto createCommentDto){
        if (!product.getIsVisible()){
            throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);
        }
        if (!product.getIsCommentable()){
            throw new ApiError(ErrorObject.PRODUCT_IS_NOT_COMMENTABLE);
        }
        if (!product.getIsPublic() && !createCommentDto.getIsCustomer()){
            throw new ApiError(ErrorObject.PRODUCT_ONLY_CUSTOMER_CAN_COMMENT);
        }
    }

    public static void check_product_is_votable(Product product, CreateVoteDto createVoteDto){
        if (!product.getIsVisible()){
            throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);
        }
        if (!product.getIsVotable()){
            throw new ApiError(ErrorObject.PRODUCT_IS_NOT_VOTABLE);
        }
        if (!product.getIsPublic() && !createVoteDto.getIsCustomer()){
            throw new ApiError(ErrorObject.PRODUCT_ONLY_CUSTOMER_CAN_VOTE);
        }
    }

}
