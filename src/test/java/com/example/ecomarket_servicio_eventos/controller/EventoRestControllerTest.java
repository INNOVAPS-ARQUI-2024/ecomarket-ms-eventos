package com.example.ecomarket_servicio_eventos.controller;


import java.util.Collections;

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




    @Test
    public void testGivenRequestForallEventsWhenObtenerEventosEndPointIsCalledThenReturnListofEventos() throws Exception {
        //Arrange
        Mockito.when(eventoService.obtenerEventos()).thenReturn(TestUtils.mockEventos());
        //Act
        RequestBuilder request = MockMvcRequestBuilders.get("/eventos")
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
        RequestBuilder request = MockMvcRequestBuilders.get("/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void testGivenValidEventIDWhenObtenerEventosEndpointIsCalledThenReturnListOfEventos() throws Exception {
        // Arrange
        Mockito.when(eventoService.obtenerEventoPorId(eq("123"))).thenReturn(TestUtils.mockEvento());
        // Act
        RequestBuilder request = MockMvcRequestBuilders.get("/eventos/123")
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
        RequestBuilder request = MockMvcRequestBuilders.post("/eventos")
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
                .post("/eventos")  // Ruta al endpoint para crear el evento
                .content(json)  // Cuerpo de la solicitud con el evento inválido
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        // Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());  // Verifica que se retorne 400 Bad Request
    }

    //PrOMOCIONAR EVENTO
    @Test
    public void testGivenValidPromocionEventoWhenEventoisPostThenReturnisOK() throws Exception {
        //Arrange
        Mockito.when(eventoService.promocionarEvento(anyString(), any(), anyString(), anyString())).thenReturn(true);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.post("/eventos/promocion/123")
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
        RequestBuilder request = MockMvcRequestBuilders.post("/eventos/promocion/123")
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
        RequestBuilder request = MockMvcRequestBuilders.put("/eventos/123")
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
        RequestBuilder request = MockMvcRequestBuilders.put("/eventos/123")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //ELIMINAR EVENTO
    @Test
    public void testGivenValidEventoWhenEventoisDeletethenReturnOK() throws Exception {
        //Arrange
        Mockito.when(eventoService.eliminarEvento(anyString(), anyString(), anyString())).thenReturn(true);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.delete("/eventos/123")
                .content(json)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGivenNonValidEventoWhenEventoisDeletethenReturnNotFound() throws Exception {
        //Arrange
        Mockito.when(eventoService.eliminarEvento(anyString(), anyString(), anyString())).thenReturn(false);
        String json = TestUtils.asJsonString(TestUtils.mockEvento());
        HttpHeaders headers = new HttpHeaders();
        headers.add("correo", "test@test.com");
        headers.add("token", "token");

        //Act
        RequestBuilder request = MockMvcRequestBuilders.delete("/eventos/123")
                .content(json)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        //Act & Assert
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
