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
import com.seyfi.review.model.request.UpdateProductDetailDto;
import com.seyfi.review.model.response.*;
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
    public GeneralResponse update(UpdateProductDetailDto updateProductDetailDto, Integer id) throws Exception {
        try {
            Optional<ProductDetail> optionalProductDetail = productDetailRepository.findByProductId(id);
            if (!optionalProductDetail.isEmpty()) {
                ProductDetail productDetail = optionalProductDetail.get();
                if (updateProductDetailDto.getIsCommentable() != null)
                    productDetail.setIsCommentable(updateProductDetailDto.getIsCommentable());

                if (updateProductDetailDto.getIsPublic() != null)
                    productDetail.setIsPublic(updateProductDetailDto.getIsPublic());

                if (updateProductDetailDto.getIsVisible() != null)
                    productDetail.setIsVisible(updateProductDetailDto.getIsVisible());

                if (updateProductDetailDto.getIsVotable() != null)
                    productDetail.setIsVotable(updateProductDetailDto.getIsVotable());

                productDetailRepository.save(productDetail);
                return new GeneralResponse(false,
                        productDetail,
                        10200000);

            } else {
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse delete(Integer id) {
        try {
            Optional<ProductDetail> productDetail = productDetailRepository.findByProductId(id);
            if(!productDetail.isEmpty()){
                productDetailRepository.deleteById(productDetail.get().getId());
                return new GeneralResponse(false,
                        "Product deleted successfully.",
                        10200000);
            } else
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
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
            Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(id);
            if (!optionalProduct.isEmpty())
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
            Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(id);
            if (!optionalProduct.isEmpty()) {
                productDetail = optionalProduct.get();
                if (!productDetail.getIsVisible())
                    throw new ApiError(ErrorObject.PRODUCT_INVISIBLE);

                if (!productDetail.getIsPublic()) {
                    comments =
                            commentRepository.
                                    findAllByIsApprovedTrueAndProductDetailAndIsCustomerTrueOrderByCreatedAtDesc(productDetail);
                    votes =
                            voteRepository.
                                    findAllByIsApprovedTrueAndProductDetailAndIsCustomerTrueOrderByCreatedAtDesc(productDetail);
                } else {
                    comments =
                            commentRepository.
                                    findAllByIsApprovedTrueAndProductDetailOrderByCreatedAtDesc(productDetail);
                    votes = voteRepository.
                            findAllByIsApprovedTrueAndProductDetailOrderByCreatedAtDesc(productDetail);
                }

                if(comments.size() > 3)
                    comments = comments.subList(0,3);

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

    @Override
    public GeneralResponse comments(Integer id, Integer size, Long sync) {
        ProductDetail productDetail;
        Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(id);
        if (!optionalProduct.isEmpty())
            productDetail = optionalProduct.get();
        else{
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
        ArrayList<Comment> comments;
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        Timestamp timestamp;
        Long timestamp_responsed;
        if(sync == 0){
            if (productDetail.getIsPublic())
                comments = (ArrayList<Comment>) commentRepository.findAllByProductDetailAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        PageRequest.ofSize(size));
            else
                comments = (ArrayList<Comment>) commentRepository.findAllByProductDetailAndIsCustomerTrueAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        PageRequest.ofSize(size));
            if (comments.size() != 0){
                Date sync_date = comments.get(comments.size()-1).getCreatedAt();
                timestamp = new Timestamp(sync_date.getTime());
                timestamp_responsed = timestamp.getTime();
            } else {
                timestamp_responsed = null;
            }
        } else {
            timestamp = new Timestamp(sync);
            Date date = new Date(timestamp.getTime());
            if (productDetail.getIsPublic())
                comments = (ArrayList<Comment>) commentRepository.findAllByProductDetailAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        date,
                        PageRequest.ofSize(size));
            else
                comments = (ArrayList<Comment>) commentRepository.findAllByProductDetailAndIsCustomerTrueAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        date,
                        PageRequest.ofSize(size));
            if (comments.size() != 0){
                Date sync_date = comments.get(comments.size()-1).getCreatedAt();
                timestamp = new Timestamp(sync_date.getTime());
                timestamp_responsed = timestamp.getTime();
            } else {
                timestamp_responsed = null;
            }
        }
        commentPageResponse.setComments(comments);
        commentPageResponse.getPage().put("allCount", comments.size());
        commentPageResponse.getPage().put("size", size);
        commentPageResponse.getPage().put("sync", timestamp_responsed);
        return new GeneralResponse(false,
                commentPageResponse,
                10200000);
    }


    @Override
    public GeneralResponse votes(Integer id, Integer size, Long sync) {
        ProductDetail productDetail;
        Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(id);
        if (!optionalProduct.isEmpty())
            productDetail = optionalProduct.get();
        else{
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
        ArrayList<Vote> votes;
        VotePageResponse votePageResponse = new VotePageResponse();
        Timestamp timestamp;
        Long timestamp_responsed;
        if(sync == 0){
            if (productDetail.getIsPublic())
                votes = (ArrayList<Vote>) voteRepository.findAllByProductDetailAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        PageRequest.ofSize(size));
            else
                votes = (ArrayList<Vote>) voteRepository.findAllByProductDetailAndIsCustomerTrueAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        PageRequest.ofSize(size));
            if (votes.size() != 0){
                Date sync_date = votes.get(votes.size()-1).getCreatedAt();
                timestamp = new Timestamp(sync_date.getTime());
                timestamp_responsed = timestamp.getTime();
            } else {
                timestamp_responsed = null;
            }
        } else {
            timestamp = new Timestamp(sync);
            Date date = new Date(timestamp.getTime());
            if (productDetail.getIsPublic())
                votes = (ArrayList<Vote>) voteRepository.findAllByProductDetailAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        date,
                        PageRequest.ofSize(size));
            else
                votes = (ArrayList<Vote>) voteRepository.findAllByProductDetailAndIsCustomerTrueAndCreatedAtBeforeAndIsApprovedTrueOrderByCreatedAtDesc(productDetail,
                        date,
                        PageRequest.ofSize(size));
            if (votes.size() != 0){
                Date sync_date = votes.get(votes.size()-1).getCreatedAt();
                timestamp = new Timestamp(sync_date.getTime());
                timestamp_responsed = timestamp.getTime();
            } else {
                timestamp_responsed = null;
            }
        }
        votePageResponse.setVotes(votes);
        votePageResponse.getPage().put("allCount", votes.size());
        votePageResponse.getPage().put("size", size);
        votePageResponse.getPage().put("sync", timestamp_responsed);
        return new GeneralResponse(false,
                votePageResponse,
                10200000);
    }
}
