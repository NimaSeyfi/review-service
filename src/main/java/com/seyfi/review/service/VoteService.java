package com.seyfi.review.service;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateVoteDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.request.UpdateVoteDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface VoteService {

    GeneralResponse create(CreateVoteDto createVoteDto) throws Exception;
    GeneralResponse update(UpdateVoteDto updateVoteDto, Long id) throws Exception;
    GeneralResponse delete(Long id);
    GeneralResponse list(Integer size, Long sync);
    GeneralResponse retrieve(Long id) throws Exception;
    GeneralResponse approve(Long id) throws Exception;

}
