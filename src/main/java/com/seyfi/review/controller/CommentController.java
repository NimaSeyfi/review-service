package com.seyfi.review.controller;

import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("comment")
public class CommentController {

    private static final Logger logger = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @PostMapping()
    ResponseEntity<GeneralResponse> create(@Valid @RequestBody CreateCommentDto createCommentDto) throws Exception {
        logger.info("Request for creating a comment : "+createCommentDto.toString());
        GeneralResponse generalResponse = commentService.create(createCommentDto);
        logger.info("Response : "+ generalResponse.toString());
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

}
