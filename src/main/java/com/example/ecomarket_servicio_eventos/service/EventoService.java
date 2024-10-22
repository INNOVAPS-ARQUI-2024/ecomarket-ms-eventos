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
    
    // Metodo para obtener eventos por vendedor
    public List<Evento> obtenerEventosPorVendedor(String sellerId) {
        return eventoRepository.findBySellerId(sellerId);
    }

    public boolean registrarUsuarioEnEvento(String idEvento, String userId) {
        // Busca el evento por ID
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Verifica si el usuario ya está registrado en el evento
        if (evento.getUserIds().contains(userId)) {
            return false; // Usuario ya está registrado
        }

        // Registra al usuario en el evento
        evento.getUserIds().add(userId);
        eventoRepository.save(evento);

        // Simulación de confirmación en pantalla
        System.out.println("Confirmación de registro enviada al usuario ID: " + userId);

        return true; // Registro exitoso
    }

    //obtener los eventos donde el cliente se ha registrados
    public List<Evento> obtenerEventosPorUsuario(String userId) {
        return eventoRepository.findAllByUserIdsContains(userId);
    }

    //obtener los eventos por fecha
    public List<Evento> obtenerEventosPorFechaHora(Date fechaHora) {
        return eventoRepository.findByFechaHora(fechaHora);
    }

    public List<Evento> obtenerEventosPorFecha(Date startOfDay, Date endOfDay) {
        return eventoRepository.findByFechaHoraBetween(startOfDay, endOfDay);
    }
    
}
