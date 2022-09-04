package com.seyfi.review.model.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreateCommentDto {

    @NotNull(message = "product_id can't be null")
    private int productId;

    @NotNull(message = "user_id can't be null")
    private int userId;

    @NotNull(message = "message content can't be null")
    @Size(min = 0, max = 500, message = "comment content size should fit in 0 to 500 characters")
    private String content;

}
