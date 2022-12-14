package com.seyfi.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class CreateVoteDto {


    @NotNull(message = "productId can't be null")
    @Positive(message = "productId should be a positive number")
    private Long productId;

    @NotNull(message = "userId can't be null")
    @Positive(message = "userId should be a positive number")
    private Long userId;

    @NotNull(message = "isCustomer can't be null")
    private Boolean isCustomer;

    @Min(value = 0, message = "minimum value of vote is 0")
    @Max(value = 10, message = "maximum value of vote is 10")
    @NotNull(message = "vote can't be null")
    private Integer vote;

}
