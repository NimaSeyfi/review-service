package com.seyfi.review.dao.repository;

import com.seyfi.review.model.entity.Comment;
import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.entity.Vote;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Integer> {

    Optional<Vote> findByUserIdAndProduct(@NotNull(message = "userId can't be null")
                                          @Positive(message = "userId should be a positive number") Integer userId,
                                          Product product);

}
