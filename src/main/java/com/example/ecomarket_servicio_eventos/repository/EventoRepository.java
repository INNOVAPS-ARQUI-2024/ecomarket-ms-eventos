package com.example.ecomarket_servicio_eventos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecomarket_servicio_eventos.entity.Evento;

public interface EventoRepository extends MongoRepository<Evento, String>{
    List<Evento> findBySellerId(String sellerId);
    List<Evento> findAllByUserIdsContains(String userId);
    List<Evento> findByFechaHora(Date fechaHora);
    List<Evento> findByFechaHoraBetween(Date startOfDay, Date endOfDay);
}
