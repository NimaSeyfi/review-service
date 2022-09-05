package com.seyfi.review.service;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface VoteService {

    GeneralResponse create(Vote vote) throws Exception;
    GeneralResponse update(Vote vote, Integer id) throws Exception;
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;

}
