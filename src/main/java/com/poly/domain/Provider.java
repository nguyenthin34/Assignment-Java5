package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "providers", indexes = {
        @Index(name = "email_UNIQUE", columnList = "email", unique = true)
})
@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @OneToMany(mappedBy = "provider")
    List<Irvourcher> Irvouchers;
}