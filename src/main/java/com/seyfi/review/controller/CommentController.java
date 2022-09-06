package com.seyfi.review.controller;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.request.CreateCommentDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@RestController
@RequestMapping("comment")
public class CommentController {

    private static final Logger logger = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @PostMapping()
    public ResponseEntity<GeneralResponse> create(@Valid @RequestBody CreateCommentDto comment)
            throws Exception {
        logger.info("Request for creating a comment : "+comment.toString());
        GeneralResponse generalResponse = commentService.create(comment);
        logger.info("Response : "+ generalResponse.toString());
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                        @PathVariable Integer id)
            throws Exception {
        logger.info("Request for retrieve a comment : "+ id.toString());
        GeneralResponse generalResponse = commentService.retrieve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Integer id)
            throws Exception {
        logger.info("Request for delete a comment : "+ id.toString());
        GeneralResponse generalResponse = commentService.delete(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list()
            throws Exception {
        logger.info("Request for retrieve all comments");
        GeneralResponse generalResponse = commentService.list();
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<GeneralResponse> approve(@Positive(message = "id should be a positive number")
                                                  @NotNull(message = "id can't be null")
                                                  @PathVariable Integer id)
            throws Exception {
        logger.info("Request for approve a comment : "+ id.toString());
        GeneralResponse generalResponse = commentService.approve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
