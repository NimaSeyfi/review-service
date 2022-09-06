package com.seyfi.review.service;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface CommentService {

    GeneralResponse create(CreateCommentDto createCommentDto  ) throws Exception;
    GeneralResponse update(UpdateCommentDto updateCommentDto, Integer id) throws Exception;
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;
    GeneralResponse approve(Integer id) throws Exception;

}
