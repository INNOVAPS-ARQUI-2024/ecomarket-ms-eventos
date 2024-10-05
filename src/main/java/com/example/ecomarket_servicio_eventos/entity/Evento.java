package com.example.ecomarket_servicio_eventos.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "eventos")
public class Evento {
    @Id
    private String eventoId;
    private String nombre;
    private String lugar;
    private String descripcion;
    private int rangoPrecios;
    private Date fechaHora;
    private String userId;
}
