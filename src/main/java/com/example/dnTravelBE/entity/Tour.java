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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer adultPrice;

    @NotNull
    private Integer childPrice;

    @NotNull
    private String description;

    @NotNull
    private Integer numberDate;

    @NotNull
    private boolean isDelete;

    @NotNull
    private String subDescription;

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<TourImage> tourImages = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "tour")
    private Set<RateTour> rateTours = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "start_location", nullable = false)
    private Province province;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

}
