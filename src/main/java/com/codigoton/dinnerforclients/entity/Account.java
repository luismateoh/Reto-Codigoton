package com.codigoton.dinnerforclients.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false,
            columnDefinition = "AUTO_INCREMENT")
    private Integer id;

    @Column(name = "client_id",
            nullable = false,
            columnDefinition = "DEFAULT '0'")
    @JoinColumn(name = "client_id")
    private Integer client_id;

    @Column(name = "balance",
            nullable = false,
            columnDefinition = "DECIMAL(10,2)) DEFAULT '0.00'")
    private Double balance;

}

