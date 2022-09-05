package com.seyfi.review.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Comment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "productId can't be null")
    @Column(name = "product_id", nullable = false)
    @Positive(message = "productId should be a positive number")
    private Integer productId;


    @NotNull(message = "userId can't be null")
    @Column(name = "user_id", nullable = false)
    @Positive(message = "userId should be a positive number")
    private Integer userId;


    @NotNull(message = "message content can't be null")
    @NotBlank(message = "content can't be blank")
    @NotEmpty(message = "content can't be empty")
    @Size(min = 1, max = 500, message = "comment content size should fit in 1 to 500 characters")
    @Column(name = "content", nullable = false, length = 500)
    private String content;

}
