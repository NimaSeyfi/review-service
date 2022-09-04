package com.seyfi.review.service.impl;

import com.seyfi.review.dao.repository.CommentRepository;
import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public GeneralResponse create(CreateCommentDto createCommentDto) throws Exception {
        Comment comment = new Comment();
        comment.setProductId(createCommentDto.getProductId());
        comment.setUserId(createCommentDto.getUserId());
        comment.setContent(createCommentDto.getContent());
        commentRepository.save(comment);
        return new GeneralResponse(false,
                comment,
                1);
    }

    @Override
    public GeneralResponse update(UpdateCommentDto updateCommentDto, Long id) throws Exception {
        return null;
    }

    @Override
    public GeneralResponse delete(Long id) {
        return null;
    }

    @Override
    public GeneralResponse list() {
        return null;
    }

    @Override
    public GeneralResponse retrieve(Long id) throws Exception {
        return null;
    }
}
