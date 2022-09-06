package com.seyfi.review.service.impl;

import com.seyfi.review.controller.CommentController;
import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.dao.repository.ProductRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.seyfi.review.utils.functions.check_product_is_commentable;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public GeneralResponse create(CreateCommentDto createCommentDto) throws Exception {
        Product product;
        if (! productRepository.existsByProductId(createCommentDto.getProductId())){
            throw new ApiError(ErrorObject.PRODUCT_DOESNT_EXIST);
        } else {
            Optional<Product> optionalProduct = productRepository.findByProductId(createCommentDto.getProductId());
            product = optionalProduct.get();
        }

        check_product_is_commentable(product, createCommentDto);

        Comment comment = new Comment();
        comment.setContent(createCommentDto.getContent());
        comment.setUserId(createCommentDto.getUserId());
        comment.setIsCustomer(createCommentDto.getIsCustomer());
        comment.setProduct(product);
        commentRepository.save(comment);
        return new GeneralResponse(false,
                comment,
                1);
    }

    @Override
    public GeneralResponse update(UpdateCommentDto updateCommentDto, Integer id) throws Exception {
        return null;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        try {
            commentRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Comment deleted successfully.",
                    1);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list() {
        try {
            ArrayList<Comment> comments = (ArrayList<Comment>) commentRepository.findAll();
            return new GeneralResponse(false,
                    comments,
                    comments.size());
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isPresent())
                return new GeneralResponse(false,
                        optionalComment.get(),
                        1);
            else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse approve(Integer id) throws Exception {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isPresent()) {
                Comment comment = optionalComment.get();
                comment.setApprovedAt(new Date());
                comment.setIsApproved(true);
                commentRepository.save(comment);
                return new GeneralResponse(false,
                        comment,
                        1);
            }else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
