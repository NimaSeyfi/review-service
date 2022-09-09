package com.seyfi.review.model.response;

import com.seyfi.review.model.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewResponse {

    private Long id;
    private Long productId;
    private Boolean isVisible;
    private Boolean isCommentable;
    private Boolean isVotable;
    private Boolean isPublic;
    private Double votesAverage;
    private Integer votesCount;
    private Integer commentsCount;
    private List<Comment> comments = new ArrayList<>();


}
