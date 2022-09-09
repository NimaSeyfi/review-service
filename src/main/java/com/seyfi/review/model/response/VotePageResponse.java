package com.seyfi.review.model.response;

import com.seyfi.review.model.entity.Vote;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class VotePageResponse {

    List<Vote> votes = new ArrayList<>();

    HashMap<String, Object> page = new HashMap<>();

}
