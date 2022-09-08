package com.seyfi.review.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "product_detail_table")
@EqualsAndHashCode
public class ProductDetail {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "productId can't be null")
    @Column(name = "product_id", nullable = false)
    @Positive(message = "productId should be a positive number")
    private Integer productId;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "is_commentable", nullable = false)
    private Boolean isCommentable;

    @Column(name = "is_votable", nullable = false)
    private Boolean isVotable;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    @UpdateTimestamp
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy="productDetail", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy="productDetail", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

}
