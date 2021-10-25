package com.codigoton.dinnerforclients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String name;
    private Integer TC;
    private String UG;
    private Double RI;
    private Double RF;
}
