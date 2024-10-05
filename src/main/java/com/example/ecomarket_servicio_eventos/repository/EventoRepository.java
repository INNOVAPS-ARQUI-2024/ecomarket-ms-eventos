package com.example.ecomarket_servicio_eventos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecomarket_servicio_eventos.entity.Evento;

public interface EventoRepository extends MongoRepository<Evento, String>{
}
