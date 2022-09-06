package com.seyfi.review.model.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="productId")
    @JsonIdentityReference(alwaysAsId=true)
    private Product product;

    @NotNull(message = "userId can't be null")
    @Column(name = "user_id", nullable = false)
    @Positive(message = "userId should be a positive number")
    private Integer userId;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "approved_at", nullable = true)
    private Date approvedAt;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @NotNull(message = "isCustomer can't be null")
    @Column(name = "is_customer", nullable = false)
    private Boolean isCustomer ;

    @NotNull(message = "message content can't be null")
    @NotBlank(message = "content can't be blank")
    @NotEmpty(message = "content can't be empty")
    @Size(min = 1, max = 500, message = "comment content size should fit in 1 to 500 characters")
    @Column(name = "content", nullable = false, length = 500)
    private String content;

}
