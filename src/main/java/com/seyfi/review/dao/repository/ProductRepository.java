package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    boolean existsByProductId(Integer id);

    Optional<Product> findByProductId(Integer id);

}
