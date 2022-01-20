package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "idvourcherdetails", indexes = {
        @Index(name = "FK_Products_IDVourcherDetails_idx", columnList = "product_id"),
        @Index(name = "FK_IDVourchers_IDVourcherDetails_idx", columnList = "IDVourcher_id")
})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idvourcherdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvourcher_detail_id", nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDVourcher_id", nullable = false)
    private Idvourcher iDVourcher;
}