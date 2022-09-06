package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> findAllByIsApprovedTrueAndProduct(Product product);

    List<Comment> findAllByIsApprovedTrueAndProductAndProductIsVisible(Product product, Boolean product_isVisible);

}
