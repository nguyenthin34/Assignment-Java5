package com.poly.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colors")
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id", nullable = false)
    private Long colorId;

    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "status", nullable = false)
    private Boolean status = false;
    @OneToMany(mappedBy = "color")
    List<Product> products;

    @OneToMany(mappedBy = "color")
    List<Irvourcherdetail> Irvourcherdetails;
}