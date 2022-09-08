package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> findAllByIsApprovedTrueAndProductDetail(ProductDetail productDetail);

    List<Comment> findAllByIsApprovedTrueAndProductDetailAndProductDetailIsVisibleOrderByCreatedAtDesc(ProductDetail productDetail,
                                                                                           Boolean product_isVisible);

    List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Comment> findAllByCreatedAtBeforeOrderByCreatedAtDesc(Date createdAt, Pageable page);
}
