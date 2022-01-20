package com.poly.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products", indexes = {
        @Index(name = "FK_Commodities_Products_idx", columnList = "commodity_id"),
        @Index(name = "FK_Sizes_Products_idx", columnList = "size_id"),
        @Index(name = "FK_IRVourchers_Products_idx", columnList = "IRVoucher_id"),
        @Index(name = "FK_Colors_Products_idx", columnList = "color_id")
})
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne(optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(name = "unitPrice")
    private Double unitPrice;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commodity_id", nullable = false)
    private Commodity commodity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IRVoucher_id", nullable = false)
    private Irvourcher irvoucher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "product")
    List<Idvourcherdetail> Idvourcherdetails;
}