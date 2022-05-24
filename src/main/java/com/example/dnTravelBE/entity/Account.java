package com.example.dnTravelBE.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull
    private String username;

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    private String password;

    @NotNull
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
