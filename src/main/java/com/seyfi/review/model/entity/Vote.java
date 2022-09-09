package com.seyfi.review.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "vote_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vote {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="productId")
    @JsonIdentityReference(alwaysAsId=true)
    private ProductDetail productDetail;

    @NotNull(message = "userId can't be null")
    @Column(name = "user_id", nullable = false)
    @Positive(message = "userId should be a positive number")
    private Long userId;

    @NotNull(message = "isCustomer can't be null")
    @Column(name = "is_customer", nullable = false)
    private Boolean isCustomer ;

    @Min(value = 0, message = "minimum value of vote is 0")
    @Max(value = 10, message = "maximum value of vote is 10")
    @NotNull(message = "vote can't be null")
    @Column(name = "vote", nullable = false)
    private Integer vote;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "approved_at", nullable = true)
    private Date approvedAt;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

}
