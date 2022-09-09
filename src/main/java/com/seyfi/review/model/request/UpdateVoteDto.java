package com.seyfi.review.model.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UpdateVoteDto {

    private Boolean isCustomer;

    @Min(value = 0, message = "minimum value of vote is 0")
    @Max(value = 10, message = "maximum value of vote is 10")
    private Integer vote;

}
