package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sizes")
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id", nullable = false)
    private Long sizeId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "status", nullable = false)
    private Boolean status = false;
    @OneToMany(mappedBy = "size")
    List<Product> products;

    @OneToMany(mappedBy = "size")
    List<Irvourcherdetail> Irvourcherdetails;

}