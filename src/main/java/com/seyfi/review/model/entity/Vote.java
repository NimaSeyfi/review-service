package com.seyfi.review.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "vote_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Vote {

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

    @Min(value = 0, message = "minimum value of vote is 0")
    @Max(value = 10, message = "maximum value of vote is 10")
    @NotNull(message = "vote can't be null")
    @Column(name = "vote", nullable = false)
    private Integer vote;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "approved_at", nullable = true)
    private Date approvedAt;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

}
