package com.example.dnTravelBE.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Thể loại tour không được để trống.")
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Tour> products = new HashSet<>();
}
