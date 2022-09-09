package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends CrudRepository<ProductDetail, Long> {

    boolean existsByProductId(Long id);

    Optional<ProductDetail> findByProductId(Long id);

    Long deleteByProductId(Long id);

    List<ProductDetail> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<ProductDetail> findAllByCreatedAtBeforeOrderByCreatedAtDesc(Date createdAt, Pageable page);


}
