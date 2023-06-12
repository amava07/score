package com.dhart.backend.model.dto;


import lombok.Data;

@Data
public class DtoRegistro {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
