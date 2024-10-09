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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    public void testGivenValidIDWhenObtenerPedidosPorIDIsCalledThenReturnPedido(){
        //Arrange
        Mockito.when(eventoRepository.findById(eq("123"))).thenReturn(Optional.ofNullable(TestUtils.mockEvento()));

        //Act
        Evento response = eventoService.obtenerEventoPorId("123");

        //Assert
        assertNotNull(response);
        verify(eventoRepository, times(1)).findById(eq("123"));
        assertEquals("123", response.getEventoId());
    }

    @Test
    public void testGivenNonValidIDWhenObtenerEventoPorIDIsCalledThenReturnNull(){
        //Arrange
        Mockito.when(eventoRepository.findById(eq("123"))).thenReturn(Optional.ofNullable(TestUtils.mockEvento()));

        //Act
        Evento response = eventoService.obtenerEventoPorId("1234");

        //Assert
        assertNull(response);
        verify(eventoRepository, times(1)).findById(eq("1234"));
    }

    @Test
    public void testGivenValidEventoObjectWhenGuardarEventoIsCallThenReturnEvento(){
        //Arrange
        Mockito.when(eventoRepository.save(any(Evento.class))).thenReturn(TestUtils.mockEvento());

        //Act
        Evento response = eventoService.guardarEvento(TestUtils.mockEvento());

        //Assert
        assertNotNull(response);
        verify(eventoRepository, times(1)).save(any(Evento.class));

    }
    @Test
    public void testGivenValidEventoObjectWhenModificarEventoIsCallThenReturnUpdateEvento(){
        //Arrange
        String eventoID = "123";
        Evento eventoExiste = TestUtils.mockEvento();
        Evento updateEvento = TestUtils.mockEvento();

        Mockito.when(eventoRepository.findById(eq(eventoID))).thenReturn(Optional.ofNullable(eventoExiste));
        Mockito.when(eventoRepository.save(any(Evento.class))).thenReturn(updateEvento);

        //Act
        Evento response = eventoService.actualizarEvento(eventoID, updateEvento);

        //Assert
        assertNotNull(response);
        assertEquals(updateEvento.getEventoId(), response.getEventoId());
        verify(eventoRepository, times(1)).findById(eq(eventoID));
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    public void testGivenNonValidPedidoObjectWhenModificarPedidoIsCallThenReturnNull(){
        //Arrange
        Evento updatePedido = TestUtils.mockEvento();

        Mockito.when(eventoRepository.findById(eq("123"))).thenReturn(Optional.ofNullable(TestUtils.mockEvento()));

        //Act
        Evento response = eventoService.actualizarEvento("1234", updatePedido);

        //Assert
        assertNull(response);
        verify(eventoRepository, times(1)).findById(eq("1234"));
        verify(eventoRepository, never()).save(any(Evento.class));

    }

    @Test
    public void testGivenExistingEventoWhenEliminarEventoIsCalledThenReturnTrue() {
        // Arrange
        String id = "123";
        String correo = "test@example.com";
        String token = "testToken";

        Mockito.when(eventoRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(eventoRepository).deleteById(id);

        // Act
        boolean response = eventoService.eliminarEvento(id, correo, token);

        // Assert
        assertTrue(response);
        verify(eventoRepository, times(1)).existsById(id);
        verify(eventoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGivenNonExistingEventoWhenEliminarEventoIsCalledThenReturnFalse() {
        // Arrange
        String id = "456";
        String correo = "test@example.com";
        String token = "testToken";

        Mockito.when(eventoRepository.existsById(id)).thenReturn(false);

        // Act
        boolean response = eventoService.eliminarEvento(id, correo, token);

        // Assert
        assertFalse(response);
        verify(eventoRepository, times(1)).existsById(id);
        verify(eventoRepository, never()).deleteById(anyString());
    }




}
