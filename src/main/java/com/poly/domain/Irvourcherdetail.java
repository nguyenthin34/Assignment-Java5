package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "irvourcherdetails", indexes = {
        @Index(name = "FK_Colors_IDVoucherDetails_idx", columnList = "color_id"),
        @Index(name = "FK_Commodities_IDVoucherDetails_idx", columnList = "commodity_id"),
        @Index(name = "FK_IDVourchers_IDVoucherDetails_idx", columnList = "irvoucher_id"),
        @Index(name = "FK_Sizes_IDVoucherDetails_idx", columnList = "size_id")
})
@Entity
public class Irvourcherdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IRVourcherDetail_id", nullable = false)
    private Long IRVourcherDetail_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "irvoucher_id", nullable = false)
    private Irvourcher irvoucher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commodity_id", nullable = false)
    private Commodity commodity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne(optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}