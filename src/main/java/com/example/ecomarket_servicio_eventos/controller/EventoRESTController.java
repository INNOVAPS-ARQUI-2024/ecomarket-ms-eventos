package com.example.ecomarket_servicio_eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoRESTController {

    @Autowired
    private EventoService eventoService;

    // Obtener todos los eventos
    @GetMapping
    public ResponseEntity<List<Evento>> obtenerEventos() {
        List<Evento> eventos = eventoService.obtenerEventos();
        return eventos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(eventos);
    }

    // Obtener un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable("id") String id) {
        Evento evento = eventoService.obtenerEventoPorId(id);
        return evento != null ? ResponseEntity.ok(evento) : ResponseEntity.status(404).body(null);
    }

    // Registrar a un usuario en un evento
    @PostMapping("/registro/{idEvento}")
    public ResponseEntity<Void> registrarseEnEvento(@PathVariable("id") String idEvento, @RequestHeader String correo,
            @RequestHeader String token) {
        boolean registrado = eventoService.registrarUsuarioEnEvento(idEvento, correo, token);
        return registrado ? ResponseEntity.status(201).build() : ResponseEntity.status(409).build(); // 409 si ya
                                                                                                     // registrado
    }

    // Publicar un nuevo evento
    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody Evento evento) {
        if (evento == null || evento.getNombre() == null) {
            return ResponseEntity.status(400).body(null); // 400 Bad Request
        }
        Evento nuevoEvento = eventoService.guardarEvento(evento);
        return ResponseEntity.status(201).body(nuevoEvento); // 201 Created
    }

    // Promocionar un evento
    @PostMapping("/promocion/{idEvento}")
    public ResponseEntity<Void> promocionarEvento(@PathVariable("idEvento") String idEvento, @RequestBody Evento detallesPromocion,
            @RequestHeader String correo, @RequestHeader String token) {
        boolean promocionado = eventoService.promocionarEvento(idEvento, detallesPromocion, correo, token);
        return promocionado ? ResponseEntity.ok().build() : ResponseEntity.status(403).build(); // 403 Forbidden

        //Los parametros en un end point no se colocan con camelCase sino con snake_case ->(id_evento)
    }

    // Actualizar un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(@PathVariable("id") String id, @RequestBody Evento detallesEvento) {
        Evento eventoActualizado = eventoService.actualizarEvento(id, detallesEvento);
        return eventoActualizado != null ? ResponseEntity.ok(eventoActualizado) : ResponseEntity.status(404).body(null);
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable("id") String id, @RequestHeader String correo,
            @RequestHeader String token) {
        boolean fueEliminado = eventoService.eliminarEvento(id, correo, token);
        return fueEliminado ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

// Encontrar eventos por id del usuario
    @GetMapping("/usuario/{sellerId}")
    public ResponseEntity<List<Evento>> obtenerEventoPorUsuario(@PathVariable("sellerId") String sellerId) {
        List<Evento> eventos = eventoService.obtenerEventosPorUsuario(sellerId);
        return ResponseEntity.ok(eventos);
    }
}
