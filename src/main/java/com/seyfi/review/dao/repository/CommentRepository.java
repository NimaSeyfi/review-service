package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {


}
