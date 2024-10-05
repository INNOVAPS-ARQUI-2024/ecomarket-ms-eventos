package com.example.ecomarket_servicio_eventos.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    // Obtener todos los eventos
    public List<Evento> obtenerEventos() {
        return eventoRepository.findAll();
    }

    // Obtener un evento por su ID
    public Evento obtenerEventoPorId(String id) {
        return eventoRepository.findById(id).orElse(null);
    }

    // Guardar un nuevo evento
    public Evento guardarEvento(Evento evento) {
        evento.setFechaHora(new Date()); // Asigna la fecha de creación
        return eventoRepository.save(evento);
    }

    // Actualizar un evento existente
    public Evento actualizarEvento(String id, Evento detallesEvento) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento != null) {
            evento.setNombre(detallesEvento.getNombre());
            evento.setDescripcion(detallesEvento.getDescripcion());
            evento.setLugar(detallesEvento.getLugar());
            evento.setFechaHora(detallesEvento.getFechaHora());
            evento.setRangoPrecios(detallesEvento.getRangoPrecios());
            evento.setFechaHora(new Date());
            evento.setUserId(detallesEvento.getUserId());
            return eventoRepository.save(evento);
        }
        return null;
    }

    // Eliminar un evento por su ID
    public boolean eliminarEvento(String id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

