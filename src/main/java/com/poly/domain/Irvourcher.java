package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "irvourchers", indexes = {
        @Index(name = "FK_Users_IDZVourchers_idx", columnList = "creator"),
        @Index(name = "FK_Providers_IDVouchers_idx", columnList = "provider_id")
})
@Entity
public class Irvourcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IRVourcher_id", nullable = false)
    private Long irVoucherId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "createDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator", nullable = false)
    private User creator;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "irvoucher")
    List<Irvourcherdetail> Irvourcherdetails;

    @OneToMany(mappedBy = "irvoucher")
    List<Product> products;
}