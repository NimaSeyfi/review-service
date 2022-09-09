package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndProductDetail(@NotNull(message = "userId can't be null")
                                          @Positive(message = "userId should be a positive number") Long userId,
                                          ProductDetail productDetail);

    List<Vote> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Vote> findAllByCreatedAtBeforeOrderByCreatedAtDesc(Date createdAt, Pageable page);

    List<Vote> findAllByIsApprovedTrueAndProductDetailAndIsCustomerTrueOrderByCreatedAtDesc(ProductDetail productDetail);

    List<Vote> findAllByIsApprovedTrueAndProductDetailOrderByCreatedAtDesc(ProductDetail productDetail);



    List<Vote> findAllByProductDetailAndIsApprovedTrueOrderByCreatedAtDesc(ProductDetail productDetail, Pageable page);

    List<Vote> findAllByProductDetailAndIsCustomerTrueAndIsApprovedTrueOrderByCreatedAtDesc(ProductDetail productDetail, Pageable page);

    List<Vote> findAllByProductDetailAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(ProductDetail productDetail, Date createdAt,
                                                                                                Pageable page);

    List<Vote> findAllByProductDetailAndIsCustomerTrueAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(ProductDetail productDetail,
                                                                                                                 Date createdAt, Pageable page);

}
