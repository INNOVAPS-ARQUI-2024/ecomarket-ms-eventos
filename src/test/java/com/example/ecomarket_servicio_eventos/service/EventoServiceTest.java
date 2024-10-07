package com.example.ecomarket_servicio_eventos.service;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.repository.EventoRepository;
import com.example.ecomarket_servicio_eventos.utils.TestUtils;
import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootApplication
@ContextConfiguration(classes = Application.class)

public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;
    @InjectMocks
    private EventoService eventoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGivenRepositoryLoadedWithEventosWhenObtenerEventosIsCalledThenReturnAllPedidos() {
        // Arrange
        Mockito.when(eventoRepository.findAll()).thenReturn(TestUtils.mockEventos());

        // Act
        List<Evento> response = eventoService.obtenerEventos();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(eventoRepository, times(1)).findAll();
    }



}
