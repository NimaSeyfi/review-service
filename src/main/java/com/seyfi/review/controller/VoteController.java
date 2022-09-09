package com.seyfi.review.controller;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.request.CreateVoteDto;
import com.seyfi.review.model.request.UpdateCommentDto;
import com.seyfi.review.model.request.UpdateVoteDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.CommentService;
import com.seyfi.review.service.VoteService;
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
@RequestMapping("vote")
public class VoteController {

    private static final Logger logger = LogManager.getLogger(VoteController.class);

    @Autowired
    private VoteService voteService;

    @PostMapping()
    public ResponseEntity<GeneralResponse> create(@Valid @RequestBody CreateVoteDto createVoteDto)
            throws Exception {
        logger.info("Request for creating a vote : "+createVoteDto.toString());
        GeneralResponse generalResponse = voteService.create(createVoteDto);
        logger.info("Response : "+ generalResponse.toString());
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(@Valid @RequestBody UpdateVoteDto updateVoteDto,
                                                  @Positive(message = "id should be a positive number")
                                                  @NotNull(message = "id can't be null")
                                                  @PathVariable Long id)
            throws Exception {
        logger.info("Request for updating a vote : "+ id.toString());
        GeneralResponse generalResponse = voteService.update(updateVoteDto, id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                        @PathVariable Long id)
            throws Exception {
        logger.info("Request for retrieve a vote : "+ id.toString());
        GeneralResponse generalResponse = voteService.retrieve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Long id)
            throws Exception {
        logger.info("Request for delete a vote : "+ id.toString());
        GeneralResponse generalResponse = voteService.delete(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list(@Positive(message = "size should be a positive number")
                                                    @NotNull(message = "size can't be null")
                                                    @RequestParam Integer size,
                                                @Positive(message = "sync should be a positive number")
                                                    @NotNull(message = "sync can't be null")
                                                    @RequestParam Long sync)
            throws Exception {
        logger.info("Request for retrieve all votes");
        GeneralResponse generalResponse = voteService.list(size, sync);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<GeneralResponse> approve(@Positive(message = "id should be a positive number")
                                                   @NotNull(message = "id can't be null")
                                                   @PathVariable Long id)
            throws Exception {
        logger.info("Request for approve a vote : "+ id.toString());
        GeneralResponse generalResponse = voteService.approve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
