package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import sample.logic.entities.Persona;
import sample.logic.persistence.IExport;
import sample.logic.persistence.IPersonaPersistence;
import sample.logic.persistence.impl.PersonaPersistence;
import sample.logic.services.IPersonaServices;
import sample.logic.services.impl.PersonaService;
import sun.rmi.server.Activation;

import javax.xml.ws.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonaTests {

    private static IPersonaServices personaServices;

    @BeforeAll
    public static void setUp(){
        personaServices = new PersonaService();
    }

    @Test
    public void shouldCheckAdd(){
        int result = 0;
        int x = 0;
        if (personaServices == null){
            result = x+1;
        }

        assertEquals(1,result);

    }
    @Test
    public void shouldCheckEdit(){
        int result = 0;
        int x = 0;
        if (personaServices == null){
            result = x;
        }

        assertEquals(x,result);

    }
    @Test
    public void shouldCheckDelete(){
        int result = 0;
        int x = 0;
        if (personaServices == null){
            result = x-1;
        }

        assertEquals(x-1,result);

    }
}

