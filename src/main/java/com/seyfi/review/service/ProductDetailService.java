package com.seyfi.review.service;

import com.seyfi.review.model.request.CreateProductDetailDto;
import com.seyfi.review.model.request.UpdateProductDetailDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface ProductDetailService {

    GeneralResponse create(CreateProductDetailDto createProductDetailDto) throws Exception;
    GeneralResponse update(UpdateProductDetailDto updateProductDetailDto, Long id) throws Exception;
    GeneralResponse delete(Long id);
    GeneralResponse list(Integer size, Long sync);
    GeneralResponse retrieve(Long id) throws Exception;
    GeneralResponse review(Long id) throws Exception;
    GeneralResponse comments(Long id, Integer size, Long sync);
    GeneralResponse votes(Long id, Integer size, Long sync);

}
