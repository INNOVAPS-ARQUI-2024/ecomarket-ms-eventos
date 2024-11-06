package com.example.ecomarket_servicio_eventos.utils;

import com.example.ecomarket_servicio_eventos.entity.Evento;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestUtils {
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Crear Evento
    public static Evento  mockEvento() {
        return Evento.builder()
                .eventoId("123")
                .nombre("Concierto de Rock")
                .lugar("Auditorio Nacional")
                .descripcion("Un concierto espectacular con las mejores bandas de rock")
                .rangoPrecios(Arrays.asList(50, 100, 150))
                .fechaHora(new Date())
                .sellerId("user123")
                .build();


    }

    public static List<Evento> mockEventos(){
        Evento evento1 = Evento.builder()
                .eventoId("123")
                .nombre("Concierto de Rock")
                .lugar("Auditorio Nacional")
                .descripcion("Un concierto espectacular con las mejores bandas de rock")
                .rangoPrecios(Arrays.asList(50, 100, 150))
                .fechaHora(new Date())
                .sellerId("user123")
                .build();

        Evento evento2 = Evento.builder()
                .eventoId("123")
                .nombre("Concierto de Rock")
                .lugar("Auditorio Nacional")
                .descripcion("Un concierto espectacular con las mejores bandas de rock")
                .rangoPrecios(Arrays.asList(50, 100, 150))
                .fechaHora(new Date())
                .sellerId("user123")
                .build();
        return Lists.newArrayList(evento1, evento2);


    }

    public static Evento mockEventoInvalido() {
        return Evento.builder()
                .eventoId("123")
                .nombre(null)  // Nombre nulo para simular un evento inválido
                .lugar("Auditorio Nacional")
                .descripcion("Un concierto espectacular con las mejores bandas de rock")
                .rangoPrecios(Arrays.asList(50, 100, 150))
                .fechaHora(new Date())
                .sellerId("user123")
                .build();
    }

    public static Evento mockDetallesPromocion() {
        return Evento.builder()
                .eventoId("123")  // Asegúrate de que este ID coincida con el que estás usando en la prueba
                .nombre("Promoción Especial")
                .lugar("Estadio Nacional")
                .descripcion("Promoción para el evento de rock")
                .rangoPrecios(Arrays.asList(30, 70, 120))
                .fechaHora(new Date())
                .sellerId("user123")
                .build();
    }


}
