package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.dao.repository.VoteRepository;
import com.seyfi.review.exception.ApiError;
import com.seyfi.review.exception.ErrorObject;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import com.seyfi.review.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private static final Logger logger = LogManager.getLogger(VoteServiceImpl.class);

    @Autowired
    VoteRepository voteRepository;

    @Override
    public GeneralResponse create(Vote vote) throws Exception {
        voteRepository.save(vote);
        return new GeneralResponse(false,
                vote,
                1);
    }

    @Override
    public GeneralResponse update(Vote vote, Integer id) throws Exception {
        return null;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        try {
            voteRepository.deleteById(id);
            return new GeneralResponse(false,
                    "Vote deleted successfully.",
                    1);
        } catch (DataAccessException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public GeneralResponse list() {
        try {
            ArrayList<Vote> votes = (ArrayList<Vote>) voteRepository.findAll();
            return new GeneralResponse(false,
                    votes,
                    votes.size());
        } catch (Exception e){
            logger.error(e);
            throw new ApiError(ErrorObject.INTERNAL_ERROR);
        }
    }

    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        try {
            Optional<Vote> optionalVote = voteRepository.findById(id);
            if (optionalVote.isPresent())
                return new GeneralResponse(false,
                        optionalVote.get(),
                        1);
            else{
                throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            throw new ApiError(ErrorObject.RESOURCE_NOT_FOUND);
        }
    }
}
