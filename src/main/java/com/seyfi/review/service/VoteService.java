package com.seyfi.review.service;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateVoteDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface VoteService {

    GeneralResponse create(CreateVoteDto createVoteDto) throws Exception;
    GeneralResponse update(Vote vote, Integer id) throws Exception;
    GeneralResponse delete(Integer id);
    GeneralResponse list(Integer size, Integer sync);
    GeneralResponse retrieve(Integer id) throws Exception;
    GeneralResponse approve(Integer id) throws Exception;

}
