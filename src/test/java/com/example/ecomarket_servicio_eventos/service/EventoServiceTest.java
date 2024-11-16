package com.example.ecomarket_servicio_eventos.service;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.repository.EventoRepository;
import com.example.ecomarket_servicio_eventos.utils.TestUtils;
import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
;

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
        boolean response = eventoService.eliminarEvento(id);

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
        boolean response = eventoService.eliminarEvento(id);

        // Assert
        assertFalse(response);
        verify(eventoRepository, times(1)).existsById(id);
        verify(eventoRepository, never()).deleteById(anyString());
    }

    //Promocionar Evento
    @Test
    public void testPromocionarEvento() {
        // Arrange
        String eventoId = "123";
        String correo = "test@test.com";
        String token = "valid_token";
        Evento detalleEvento = TestUtils.mockDetallesPromocion();

        // Act
        boolean resultado = eventoService.promocionarEvento(eventoId, detalleEvento, correo, token);

        // Assert
        Assertions.assertTrue(resultado, "El método debe retornar true mientras sea un placeholder.");
    }

    //Eventos por Vendedor
    @Test
    public void testObtenerEventosPorVendedor() {
        // Arrange
        String sellerId = "user123";
        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoRepository.findBySellerId(sellerId)).thenReturn(eventosMock);

        // Act
        List<Evento> eventos = eventoService.obtenerEventosPorVendedor(sellerId);

        // Assert
        Assertions.assertNotNull(eventos, "La lista de eventos no debe ser nula");
        Assertions.assertEquals(eventosMock.size(), eventos.size(), "Debe retornar la cantidad correcta de eventos");
        Mockito.verify(eventoRepository, Mockito.times(1)).findBySellerId(sellerId);
    }

    //Registrar Usuario para Evento
    @Test
    public void testRegistrarUsuarioEnEvento() {
        // Arrange
        String eventoId = "123";
        String userId = "user456";
        Evento mockEvento = TestUtils.mockEvento();
        mockEvento.setUserIds(new ArrayList<>()); // Simula que el usuario no está registrado

        Mockito.when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(mockEvento));

        // Act
        boolean registrado = eventoService.registrarUsuarioEnEvento(eventoId, userId);

        // Assert
        Assertions.assertTrue(registrado, "El usuario debería registrarse correctamente");
        Mockito.verify(eventoRepository, Mockito.times(1)).save(mockEvento);
        Assertions.assertTrue(mockEvento.getUserIds().contains(userId), "El ID del usuario debe ser agregado al evento");
    }

    //Eventos por Usuario
    @Test
    public void testObtenerEventosPorUsuario() {
        // Arrange
        String userId = "user456";
        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoRepository.findAllByUserIdsContains(userId)).thenReturn(eventosMock);

        // Act
        List<Evento> eventos = eventoService.obtenerEventosPorUsuario(userId);

        // Assert
        Assertions.assertNotNull(eventos, "La lista de eventos no debe ser nula");
        Assertions.assertEquals(eventosMock.size(), eventos.size(), "Debe retornar la cantidad correcta de eventos");
        Mockito.verify(eventoRepository, Mockito.times(1)).findAllByUserIdsContains(userId);
    }

    //Eventos por Fecha y Hora
    @Test
    public void testObtenerEventosPorFechaHora() {
        // Arrange
        Date fechaHora = new Date();
        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoRepository.findByFechaHora(fechaHora)).thenReturn(eventosMock);

        // Act
        List<Evento> eventos = eventoService.obtenerEventosPorFechaHora(fechaHora);

        // Assert
        Assertions.assertNotNull(eventos, "La lista de eventos no debe ser nula");
        Assertions.assertEquals(eventosMock.size(), eventos.size(), "Debe retornar la cantidad correcta de eventos");
        Mockito.verify(eventoRepository, Mockito.times(1)).findByFechaHora(fechaHora);
    }

    //Eventos por Fecha
    @Test
    public void testObtenerEventosPorFecha() {
        // Arrange
        Date startOfDay = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // Ayer
        Date endOfDay = new Date();
        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoRepository.findByFechaHoraBetween(startOfDay, endOfDay)).thenReturn(eventosMock);

        // Act
        List<Evento> eventos = eventoService.obtenerEventosPorFecha(startOfDay, endOfDay);

        // Assert
        Assertions.assertNotNull(eventos, "La lista de eventos no debe ser nula");
        Assertions.assertEquals(eventosMock.size(), eventos.size(), "Debe retornar la cantidad correcta de eventos");
        Mockito.verify(eventoRepository, Mockito.times(1)).findByFechaHoraBetween(startOfDay, endOfDay);
    }

}
