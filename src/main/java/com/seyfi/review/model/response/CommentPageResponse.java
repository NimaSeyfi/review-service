package com.seyfi.review.model.response;

import com.seyfi.review.model.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class CommentPageResponse {

    List<Comment> comments = new ArrayList<>();

    HashMap<String, Object> page = new HashMap<>();

}
