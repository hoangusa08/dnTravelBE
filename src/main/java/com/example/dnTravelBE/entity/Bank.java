package com.example.dnTravelBE.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Tên ngân hàng không được để trống.")
    private String name;

    @OneToMany(mappedBy = "bank")
    private Set<Provider> providers = new HashSet<>();
}