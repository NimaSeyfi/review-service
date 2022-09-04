package com.seyfi.review.service;

import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;

public interface CommentService {

    GeneralResponse create(CreateCommentDto createCommentDto) throws Exception;
    GeneralResponse update(UpdateCommentDto updateCommentDto, Long id) throws Exception;
    GeneralResponse delete(Long id);
    GeneralResponse list();
    GeneralResponse retrieve(Long id) throws Exception;

}
