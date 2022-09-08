package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.dao.repository.ProductDetailRepository;
import com.seyfi.review.dao.repository.VoteRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateProductDetailDto;
import com.seyfi.review.model.response.CommentPageResponse;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.model.response.ProductDetailPageResponse;
import com.seyfi.review.model.response.ReviewResponse;
import com.seyfi.review.service.ProductDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    private static final Logger logger = LogManager.getLogger(ProductDetailServiceImpl.class);

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VoteRepository voteRepository;

    @Override
    public GeneralResponse create(CreateProductDetailDto createProductDetailDto) throws Exception {
        if (!productDetailRepository.existsByProductId(createProductDetailDto.getProductId())) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductId(createProductDetailDto.getProductId());
            productDetail.setIsCommentable(createProductDetailDto.getIsCommentable());
            productDetail.setIsPublic(createProductDetailDto.getIsPublic());
            productDetail.setIsVisible(createProductDetailDto.getIsVisible());
            productDetail.setIsVotable(createProductDetailDto.getIsVotable());
            productDetailRepository.save(productDetail);
            return new GeneralResponse(false,
                    productDetail,
                    10200000);
        } else {
            throw new ApiError(ErrorObject.PRODUCT_EXIST);
        }
    }

    @Override
    public GeneralResponse update(ProductDetail vote, Integer id) throws Exception {
        return null;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        try {
            productDetailRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Product deleted successfully.",
                    10200000);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list(Integer size, Long sync) {
        try {
            ArrayList<ProductDetail> productDetails;
            ProductDetailPageResponse producetDetailPageResponse = new ProductDetailPageResponse();
            Timestamp timestamp;
            Long timestamp_responsed;
            if(sync == 0){
                productDetails = (ArrayList<ProductDetail>) productDetailRepository.
                        findAllByOrderByCreatedAtDesc(PageRequest.ofSize(size));
                if (productDetails.size() != 0){
                    Date sync_date = productDetails.get(productDetails.size()-1).getCreatedAt();
                    timestamp = new Timestamp(sync_date.getTime());
                    timestamp_responsed = timestamp.getTime();
                } else {
                    timestamp_responsed = null;
                }
            } else {
                timestamp = new Timestamp(sync);
                Date date = new Date(timestamp.getTime());
                productDetails = (ArrayList<ProductDetail>) productDetailRepository.findAllByCreatedAtBeforeOrderByCreatedAtDesc(date,
                        PageRequest.ofSize(size));
                if (productDetails.size() != 0){
                    Date sync_date = productDetails.get(productDetails.size()-1).getCreatedAt();
                    timestamp = new Timestamp(sync_date.getTime());
                    timestamp_responsed = timestamp.getTime();
                } else {
                    timestamp_responsed = null;
                }
            }
            producetDetailPageResponse.setProductDetails(productDetails);
            producetDetailPageResponse.getPage().put("allCount", commentRepository.count());
            producetDetailPageResponse.getPage().put("size", size);
            producetDetailPageResponse.getPage().put("sync", timestamp_responsed);
            return new GeneralResponse(false,
                    producetDetailPageResponse,
                    10200000);
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        try {
            Optional<ProductDetail> optionalProduct = productDetailRepository.findById(id);
            if (optionalProduct.isPresent())
                return new GeneralResponse(false,
                        optionalProduct.get(),
                        10200000);
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
            ProductDetail productDetail;
            List<Comment> comments;
            List<Vote> votes;
            Optional<ProductDetail> optionalProduct = productDetailRepository.findById(id);
            if (optionalProduct.isPresent()) {
                productDetail = optionalProduct.get();
                if (!productDetail.getIsVisible())
                    throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);

                comments =
                        commentRepository.
                                findAllByIsApprovedTrueAndProductDetailAndProductDetailIsVisibleOrderByCreatedAtDesc(productDetail,
                        true);
                if(comments.size() > 3)
                    comments = comments.subList(0,3);

                votes = voteRepository.findAllByIsApprovedTrue();
                Double voteAverage = votes.stream()
                        .mapToDouble(d -> d.getVote())
                        .average()
                        .orElse(0.0);

                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setProductId(productDetail.getProductId());
                reviewResponse.setId(productDetail.getId());
                reviewResponse.setIsCommentable(productDetail.getIsCommentable());
                reviewResponse.setComments(comments);
                reviewResponse.setCommentsCount(comments.size());
                reviewResponse.setIsPublic(productDetail.getIsPublic());
                reviewResponse.setIsVisible(productDetail.getIsVisible());
                reviewResponse.setIsVotable(productDetail.getIsVotable());
                reviewResponse.setVotesCount(votes.size());
                reviewResponse.setVotesAverage(voteAverage);
                return new GeneralResponse(false,
                        reviewResponse,
                        10200000);
            }else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
