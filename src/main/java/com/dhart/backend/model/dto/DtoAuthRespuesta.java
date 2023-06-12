package com.dhart.backend.model.dto;

import lombok.Data;

//Esta clase va a ser la que nos devolverá la información con el token y el tipo que tenga este
@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String firstName;
    private String lastName;
    private String role;

    public DtoAuthRespuesta(String accessToken) {
        this.accessToken = accessToken;
    }

    // Nuevo constructor que toma todos los argumentos
    public DtoAuthRespuesta(String accessToken, String firstName, String lastName, String role) {
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}


