package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commodities", indexes = {
        @Index(name = "FK_categories_commodities_idx", columnList = "category_id")
})
@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commodity_id", nullable = false)
    private Long commodityId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "commodity")
    List<Irvourcherdetail> Irvourcherdetails;
}