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

    // Obtener un evento por ID
    public Evento obtenerEventoPorId(String id) {
        return eventoRepository.findById(id).orElse(null);
    }

    // Registrar usuario en un evento
    public boolean registrarUsuarioEnEvento(String IdEvento, String correo, String token) {
        // Lógica para verificar el token y registrar al usuario
        // Retornar true si el registro fue exitoso o false si ya está registrado
        return true; // Placeholder
    }

    // Guardar un nuevo evento
    public Evento guardarEvento(Evento evento) {
        evento.setFechaHora(new Date()); // Asigna la fecha de creación
        return eventoRepository.save(evento);
    }

    // Promocionar un evento
    public boolean promocionarEvento(String IdEvento, Evento detalleEvento, String correo, String token) {
        // Lógica para promocionar el evento y verificar permisos
        return true; // Placeholder
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
            return eventoRepository.save(evento);
        }
        return null;
    }

    // Eliminar un evento
    public boolean eliminarEvento(String id, String correo, String token) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
