package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.dao.repository.ProductRepository;
import com.seyfi.review.dao.repository.VoteRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.model.response.ReviewResponse;
import com.seyfi.review.service.ProductService;
import com.seyfi.review.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VoteRepository voteRepository;

    @Override
    public GeneralResponse create(Product product) throws Exception {
        if (!productRepository.existsByProductId(product.getProductId())) {
            productRepository.save(product);
            return new GeneralResponse(false,
                    product,
                    1);
        } else {
            throw new ApiError(ErrorObject.PRODUCT_EXIST);
        }
    }

    @Override
    public GeneralResponse update(Product vote, Integer id) throws Exception {
        return null;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Product deleted successfully.",
                    1);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list() {
        try {
            ArrayList<Product> products = (ArrayList<Product>) productRepository.findAll();
            return new GeneralResponse(false,
                    products,
                    products.size());
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent())
                return new GeneralResponse(false,
                        optionalProduct.get(),
                        1);
            else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse review(Integer id) throws Exception {
        try {
            Product product;
            List<Comment> comments;
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
                if (!product.getIsVisible())
                    throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);

                comments =
                        commentRepository.
                                findAllByIsApprovedTrueAndProductAndProductIsVisibleOrderByCreatedAtDesc(product,
                        true);
                if(comments.size() > 3)
                    comments = comments.subList(0,3);

                Double voteAverage = product.getVotes().stream()
                        .mapToDouble(d -> d.getVote())
                        .average()
                        .orElse(0.0);

                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setProductId(product.getProductId());
                reviewResponse.setId(product.getId());
                reviewResponse.setIsCommentable(product.getIsCommentable());
                reviewResponse.setComments(comments);
                reviewResponse.setCommentsCount(comments.size());
                reviewResponse.setIsPublic(product.getIsPublic());
                reviewResponse.setIsVisible(product.getIsVisible());
                reviewResponse.setIsVotable(product.getIsVotable());
                reviewResponse.setVotesCount(product.getVotes().size());
                reviewResponse.setVotesAverage(voteAverage);
                return new GeneralResponse(false,
                        reviewResponse,
                        1);
            }else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
