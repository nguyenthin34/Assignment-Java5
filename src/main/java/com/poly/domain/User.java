package com.poly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "fullname", nullable = false, length = 55)
    private String fullname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status = false;


    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "creator")
    List<Idvourcher> Idvourchers;

    @OneToMany(mappedBy = "creator")
    List<Irvourcher> Irvouchers;
}