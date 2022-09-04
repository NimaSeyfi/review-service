package com.seyfi.review.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rate_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Rate {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "rate", nullable = false, length = 1)
    private int rate;

}
