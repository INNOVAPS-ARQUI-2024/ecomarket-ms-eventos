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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoRESTController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> obtenerEventos() {
        return eventoService.obtenerEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable String id) {
        Evento evento = eventoService.obtenerEventoPorId(id);
        if (evento != null) {
            return ResponseEntity.ok(evento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Evento crearEvento(@RequestBody Evento evento) {
        return eventoService.guardarEvento(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(@PathVariable String id, @RequestBody Evento detallesEvento) {
        Evento eventoActualizado = eventoService.actualizarEvento(id, detallesEvento);
        if (eventoActualizado != null) {
            return ResponseEntity.ok(eventoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable String id) {
        boolean fueEliminado = eventoService.eliminarEvento(id);
        if (fueEliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

