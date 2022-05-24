package com.example.dnTravelBE.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull
    private String nameCompany;

    @NotNull
    private String owner;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String bankNumber;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
}
