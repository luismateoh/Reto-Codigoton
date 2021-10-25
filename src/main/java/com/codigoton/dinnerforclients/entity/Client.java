package com.codigoton.dinnerforclients.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false,
            columnDefinition = "USING BTREE")
    private Integer id;

    @Column(name = "code",
            nullable = false,
            length = 20)
    private String code;

    @Column(name = "male",
            nullable = false,
            columnDefinition = "TINYINT(4) COLLATE 'utf8mb3_general_ci'")
    private Boolean male;

    @Column(name = "type",
            nullable = false)
    private Integer type;

    @Column(name = "location",
            nullable = false,
            columnDefinition = "COLLATE 'utf8mb3_general_ci'",
            length = 50)
    private String location;

    @Column(name = "company",
            nullable = false,
            columnDefinition = "COLLATE 'utf8mb3_general_ci'",
            length = 50)
    private String company;

    @Column(name = "encrypt",
            nullable = false,
            columnDefinition = "TINYINT(1)")
    private Boolean encrypt;

    @OrderBy("id, balance DESC")
    @OneToMany(mappedBy = "client_id", orphanRemoval = true)
    private List<Account> accounts;

}


