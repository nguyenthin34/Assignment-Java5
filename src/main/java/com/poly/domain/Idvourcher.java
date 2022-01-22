package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Table(name = "idvourchers", indexes = {
        @Index(name = "FK_Users_IDVourchers_idx", columnList = "creator")
})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idvourcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDVourcher_id", nullable = false)
    private Long IDVourcher_id;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "export", nullable = false)
    private Boolean export;

    @OneToMany(mappedBy = "iDVourcher")
    List<Idvourcherdetail> idvourcherdetailList;
}