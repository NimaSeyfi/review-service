package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.ProductDetailRepository;
import com.seyfi.review.dao.repository.VoteRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.ProductDetail;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateVoteDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.request.UpdateVoteDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.model.response.VotePageResponse;
import com.seyfi.review.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.seyfi.review.utils.functions.check_product_is_votable;

@Service
public class VoteServiceImpl implements VoteService {

    private static final Logger logger = LogManager.getLogger(VoteServiceImpl.class);

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public GeneralResponse create(CreateVoteDto createVoteDto) throws Exception {
        ProductDetail productDetail;
        try {
            Optional<ProductDetail> optionalProduct = productDetailRepository.findByProductId(createVoteDto.getProductId());
            productDetail = optionalProduct.get();
        }catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.PRODUCT_DOESNT_EXIST);
        }
        check_product_is_votable(productDetail, createVoteDto);

        Vote vote;
        try {
            Optional<Vote> optionalVote = voteRepository.findByUserIdAndProductDetail(createVoteDto.getUserId(),
                    productDetail);
            vote = optionalVote.get();
        }catch (NoSuchElementException e){
            vote = new Vote();
        }
        vote.setUserId(createVoteDto.getUserId());
        vote.setIsCustomer(createVoteDto.getIsCustomer());
        vote.setVote(createVoteDto.getVote());
        vote.setProductDetail(productDetail);
        voteRepository.save(vote);
        return new GeneralResponse(false,
                vote,
                10200000);
    }

    @Override
    public GeneralResponse update(UpdateVoteDto updateVoteDto, Long id) throws Exception {
        try {
            Optional<Vote> optionalVote = voteRepository.findById(id);
            if (!optionalVote.isEmpty()) {
                Vote vote = optionalVote.get();
                if (updateVoteDto.getIsCustomer() != null)
                    vote.setIsCustomer(updateVoteDto.getIsCustomer());

                if (updateVoteDto.getVote() != null)
                    vote.setVote(updateVoteDto.getVote());

                voteRepository.save(vote);
                return new GeneralResponse(false,
                        vote,
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
            voteRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Vote deleted successfully.",
                    10200000);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list(Integer size, Long sync) {
        try {
            ArrayList<Vote> votes;
            VotePageResponse votePageResponse = new VotePageResponse();
            Timestamp timestamp;
            Long timestamp_responsed;
            if(sync == 0){
                votes = (ArrayList<Vote>) voteRepository.findAllByOrderByCreatedAtDesc(PageRequest.ofSize(size));
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
                votes = (ArrayList<Vote>) voteRepository.findAllByCreatedAtBeforeOrderByCreatedAtDesc(date,
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
            votePageResponse.getPage().put("allCount", voteRepository.count());
            votePageResponse.getPage().put("size", size);
            votePageResponse.getPage().put("sync", timestamp_responsed);
            return new GeneralResponse(false,
                    votePageResponse,
                    10200000);
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Long id) throws Exception {
        try {
            Optional<Vote> optionalVote = voteRepository.findById(id);
            if (!optionalVote.isEmpty())
                return new GeneralResponse(false,
                        optionalVote.get(),
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
            Optional<Vote> optionalVote = voteRepository.findById(id);
            if (!optionalVote.isEmpty()) {
                Vote vote = optionalVote.get();
                vote.setApprovedAt(new Date());
                vote.setIsApproved(true);
                voteRepository.save(vote);
                return new GeneralResponse(false,
                        vote,
                        10200000);
            }else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
