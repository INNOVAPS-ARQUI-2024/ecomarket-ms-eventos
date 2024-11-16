package com.example.ecomarket_servicio_eventos.controller;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.example.ecomarket_servicio_eventos.service.EventoService;
import com.example.ecomarket_servicio_eventos.utils.TestUtils;

@WebMvcTest(EventoRESTController.class)
public class EventoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventoService eventoService;



    //Obtener Todos los Eventos
    @Test
    public void testGivenRequestForallEventsWhenObtenerEventosEndPointIsCalledThenReturnListofEventos() throws Exception {
        //Arrange
        Mockito.when(eventoService.obtenerEventos()).thenReturn(TestUtils.mockEventos());
        //Act
        RequestBuilder request = MockMvcRequestBuilders.get("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGivenRequestForNonEventosWhenObtenerEventosEndPointIsCalledThenReturnNoContent() throws Exception{
        //Arrange
        Mockito.when(eventoService.obtenerEventos()).thenReturn(Collections.emptyList());
        //Act
        RequestBuilder request = MockMvcRequestBuilders.get("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
    //Obtener Evento por ID
    @Test
    public void testGivenValidEventIDWhenObtenerEventosEndpointIsCalledThenReturnListOfEventos() throws Exception {
        // Arrange
        Mockito.when(eventoService.obtenerEventoPorId(eq("123"))).thenReturn(TestUtils.mockEvento());
        // Act
        RequestBuilder request = MockMvcRequestBuilders.get("/api/eventos/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);


        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




    //Crear Evento
    @Test
    public void testGivenValidEventoWhenEventoIsPostThenReturnisCreated() throws Exception {
        // Arrange
        Mockito.when(eventoService.guardarEvento(any())).thenReturn(TestUtils.mockEvento());
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        // Act
        RequestBuilder request = MockMvcRequestBuilders.post("/api/eventos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        // Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void testGivenNonValidEventoWhenEventoIsPostThenReturnBadRequest() throws Exception {
        // Arrange
        Evento eventoInvalido = TestUtils.mockEventoInvalido();  // Usar el nuevo método para crear un evento inválido
        String json = TestUtils.asJsonString(eventoInvalido);  // Convertir a JSON

        // Act
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/eventos")  // Ruta al endpoint para crear el evento
                .content(json)  // Cuerpo de la solicitud con el evento inválido
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        // Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());  // Verifica que se retorne 400 Bad Request
    }


    //PROMOCIONAR EVENTO
    @Test
    public void testGivenValidPromocionEventoWhenEventoisPostThenReturnisOK() throws Exception {
        //Arrange
        Mockito.when(eventoService.promocionarEvento(anyString(), any(), anyString(), anyString())).thenReturn(true);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.post("/api/eventos/promocion/123")
                .content(json)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testGivenNonValidPromocionEventoWhenEventoisPostThenReturnisNoContent() throws Exception {
        //Arrange
        Mockito.when(eventoService.promocionarEvento(anyString(), any(), anyString(), anyString())).thenReturn(false);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.post("/api/eventos/promocion/123")
                .content(json)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }




    //ACTUALIZAR EVENTO
    @Test
    public void testGivenValidEventoWhenEventoisPutThenReturnisOK() throws Exception {
        //Arrange
        Mockito.when(eventoService.actualizarEvento(eq("123"), any(Evento.class))).thenReturn(TestUtils.mockEvento());
        String json = TestUtils.asJsonString(TestUtils.mockEvento());

        //Act
        RequestBuilder request = MockMvcRequestBuilders.put("/api/eventos/123")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGivenNonValidEventoWhenEventoIsPutThenReturnNotFound() throws Exception {
        //Arrange
        Mockito.when(eventoService.actualizarEvento(eq("123"), any(Evento.class))).thenReturn(null);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());

        //Act
        RequestBuilder request = MockMvcRequestBuilders.put("/api/eventos/123")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //ELIMINAR EVENTO
    @Test
    public void testGivenValidEventoWhenEventoisDeleteThenReturnNoContent() throws Exception {
        // Arrange
        Mockito.when(eventoService.eliminarEvento(anyString())).thenReturn(true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        // Act
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/eventos/123")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        // Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void testGivenNonValidEventoWhenEventoisDeletethenReturnNotFound() throws Exception {
        //Arrange
        Mockito.when(eventoService.eliminarEvento(anyString())).thenReturn(false);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/eventos/123")
                .content(json)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Registrar usuario en un evento
    @Test
    public void testRegistrarUsuario_RegistroExitoso() throws Exception {
        // Arrange
        String eventoId = "123";
        String userId = "user456";
        Map<String, String> payload = Map.of("userId", userId);
        Mockito.when(eventoService.registrarUsuarioEnEvento(eventoId, userId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/eventos/registrar/{eventoId}", eventoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que el servicio fue llamado con los parámetros correctos
        Mockito.verify(eventoService, Mockito.times(1)).registrarUsuarioEnEvento(eventoId, userId);
    }


    @Test
    public void testRegistrarUsuario_UsuarioYaRegistrado() throws Exception {
        // Arrange
        String eventoId = "123";
        String userId = "user456";
        Map<String, String> payload = Map.of("userId", userId);
        Mockito.when(eventoService.registrarUsuarioEnEvento(eventoId, userId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/eventos/registrar/{eventoId}", eventoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());


        // Verificar que el servicio fue llamado con los parámetros correctos
        Mockito.verify(eventoService, Mockito.times(1)).registrarUsuarioEnEvento(eventoId, userId);
    }

    // Obtener eventos por fecha
    @Test
    public void testObtenerEventosPorFecha_FechaValidaConEventos() throws Exception {
        // Arrange
        String fecha = "2024-11-15";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaParsed = sdf.parse(fecha);

        // Definir los valores de inicio y fin del día para la fecha proporcionada
        Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(fechaParsed);
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(fechaParsed);
        endOfDay.set(Calendar.HOUR_OF_DAY, 23);
        endOfDay.set(Calendar.MINUTE, 59);
        endOfDay.set(Calendar.SECOND, 59);
        endOfDay.set(Calendar.MILLISECOND, 999);

        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoService.obtenerEventosPorFecha(startOfDay.getTime(), endOfDay.getTime())).thenReturn(eventosMock);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/fecha/{fecha}", fecha)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(eventosMock.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventoId").value(eventosMock.get(0).getEventoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].eventoId").value(eventosMock.get(1).getEventoId()));

        // Verificar que el servicio fue llamado con los parámetros correctos
        Mockito.verify(eventoService, Mockito.times(1)).obtenerEventosPorFecha(startOfDay.getTime(), endOfDay.getTime());
    }

    @Test
    public void testObtenerEventosPorFecha_FechaValidaSinEventos() throws Exception {
        // Arrange
        String fecha = "2024-11-15";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaParsed = sdf.parse(fecha);

        // Definir los valores de inicio y fin del día para la fecha proporcionada
        Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(fechaParsed);
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(fechaParsed);
        endOfDay.set(Calendar.HOUR_OF_DAY, 23);
        endOfDay.set(Calendar.MINUTE, 59);
        endOfDay.set(Calendar.SECOND, 59);
        endOfDay.set(Calendar.MILLISECOND, 999);

        Mockito.when(eventoService.obtenerEventosPorFecha(startOfDay.getTime(), endOfDay.getTime())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/fecha/{fecha}", fecha)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        // Verificar que el servicio fue llamado con los parámetros correctos
        Mockito.verify(eventoService, Mockito.times(1)).obtenerEventosPorFecha(startOfDay.getTime(), endOfDay.getTime());
    }



    @Test
    public void testObtenerEventosPorFecha_FechaInvalida() throws Exception {
        // Arrange
        String fecha = "invalid-date";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/fecha/{fecha}", fecha)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verificar que el servicio no fue llamado debido al error de formato
        Mockito.verify(eventoService, Mockito.never()).obtenerEventosPorFecha(Mockito.any(), Mockito.any());
    }



    // Obtener eventos por usuario
    @Test
    public void testObtenerEventosPorUsuario() throws Exception {
        Mockito.when(eventoService.obtenerEventosPorUsuario(eq("user123")))
                .thenReturn(TestUtils.mockEventos());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/registrados/user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testObtenerEventosPorUsuarioSinEventos() throws Exception {
        Mockito.when(eventoService.obtenerEventosPorUsuario(eq("user123")))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/registrados/user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Eventos por Vendedor
    @Test
    public void testObtenerEventosPorVendedor() throws Exception {
        // Arrange
        String sellerId = "user123";
        List<Evento> eventosMock = TestUtils.mockEventos();
        Mockito.when(eventoService.obtenerEventosPorVendedor(sellerId)).thenReturn(eventosMock);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/usuario/{sellerId}", sellerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(eventosMock.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].eventoId").value(eventosMock.get(0).getEventoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].eventoId").value(eventosMock.get(1).getEventoId()));

        // Verificar que el servicio fue llamado con los parámetros correctos
        Mockito.verify(eventoService, Mockito.times(1)).obtenerEventosPorVendedor(sellerId);
    }



}
