package com.seyfi.review.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_table")
@ToString
@EqualsAndHashCode
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "productId can't be null")
    @Column(name = "product_id", nullable = false)
    @Positive(message = "productId should be a positive number")
    private Integer productId;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = true;

    @Column(name = "is_commentable", nullable = false)
    private Boolean isCommentable = true;

    @Column(name = "is_votable", nullable = false)
    private Boolean isVotable = true;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<Comment> comments = new ArrayList<>();

}
