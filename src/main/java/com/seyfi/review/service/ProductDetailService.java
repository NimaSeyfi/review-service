package com.seyfi.review.service;

import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.request.CreateProductDetailDto;
import com.seyfi.review.model.request.UpdateProductDetailDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface ProductDetailService {

    GeneralResponse create(CreateProductDetailDto createProductDetailDto) throws Exception;
    GeneralResponse update(UpdateProductDetailDto updateProductDetailDto, Integer id) throws Exception;
    GeneralResponse delete(Integer id);
    GeneralResponse list(Integer size, Long sync);
    GeneralResponse retrieve(Integer id) throws Exception;
    GeneralResponse review(Integer id) throws Exception;
    GeneralResponse comments(Integer id, Integer size, Long sync);
    GeneralResponse votes(Integer id, Integer size, Long sync);

}
