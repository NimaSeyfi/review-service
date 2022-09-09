package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.dao.repository.ProductDetailRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.CommentPageResponse;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.model.response.VotePageResponse;
import com.seyfi.review.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static com.seyfi.review.utils.functions.check_product_is_commentable;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public GeneralResponse create(CreateCommentDto createCommentDto) throws Exception {
        ProductDetail productDetail;
        try {
            Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(createCommentDto.getProductId());
            productDetail = optionalProduct.get();
        }catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.PRODUCT_DOESNT_EXIST);
        }

        check_product_is_commentable(productDetail, createCommentDto);

        Comment comment = new Comment();
        comment.setContent(createCommentDto.getContent());
        comment.setUserId(createCommentDto.getUserId());
        comment.setIsCustomer(createCommentDto.getIsCustomer());
        comment.setProductDetail(productDetail);
        commentRepository.save(comment);
        return new GeneralResponse(false,
                comment,
                10200000);
    }

    @Override
    public GeneralResponse update(UpdateCommentDto updateCommentDto, Long id) throws Exception {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isEmpty()) {
                Comment comment = optionalComment.get();
                if (updateCommentDto.getIsCustomer() != null)
                    comment.setIsCustomer(updateCommentDto.getIsCustomer());

                if (updateCommentDto.getContent() != null)
                    comment.setContent(updateCommentDto.getContent());

                commentRepository.save(comment);
                return new GeneralResponse(false,
                        comment,
                        10200000);

            } else {
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse delete(Long id) {
        try {
            commentRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Comment deleted successfully.",
                    10200000);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list(Integer size, Long sync) {
        try {
            ArrayList<Comment> comments;
            CommentPageResponse commentPageResponse = new CommentPageResponse();
            Timestamp timestamp;
            Long timestamp_responsed;
            if(sync == 0){
                comments = (ArrayList<Comment>) commentRepository.findAllByOrderByCreatedAtDesc(PageRequest.ofSize(size));
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
                comments = (ArrayList<Comment>) commentRepository.findAllByCreatedAtBeforeOrderByCreatedAtDesc(date,
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
            commentPageResponse.getPage().put("allCount", commentRepository.count());
            commentPageResponse.getPage().put("size", size);
            commentPageResponse.getPage().put("sync", timestamp_responsed);
            return new GeneralResponse(false,
                    commentPageResponse,
                    10200000);
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Long id) throws Exception {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isEmpty())
                return new GeneralResponse(false,
                        optionalComment.get(),
                        10200000);
            else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse approve(Long id) throws Exception {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isEmpty()) {
                Comment comment = optionalComment.get();
                comment.setApprovedAt(new Date());
                comment.setIsApproved(true);
                commentRepository.save(comment);
                return new GeneralResponse(false,
                        comment,
                        10200000);
            }else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
