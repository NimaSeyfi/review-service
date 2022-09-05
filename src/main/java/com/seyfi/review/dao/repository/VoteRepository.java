package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Integer> {


}
