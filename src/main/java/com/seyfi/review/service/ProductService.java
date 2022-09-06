package com.seyfi.review.service;

import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.response.GeneralResponse;

public interface ProductService {

    GeneralResponse create(Product product) throws Exception;
    GeneralResponse update(Product product, Integer id) throws Exception;
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;
    GeneralResponse review(Integer id) throws Exception;

}
