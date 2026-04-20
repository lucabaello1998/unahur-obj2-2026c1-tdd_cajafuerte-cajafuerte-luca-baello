package ar.edu.unahur.obj2.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CajaFuerteTest {

    @Test
    void alCrearseUnaCajaFuerteEstaCerrada() {
        CajaFuerte caja = new CajaFuerte("1234");

        assertFalse(caja.estaAbierta());
    }

    @Test
    void abriendoLaCajaConElCodigoCorrectoLaabre() {
        CajaFuerte caja = new CajaFuerte("1234");

        caja.abrir("1234");

        assertTrue(caja.estaAbierta());
    }

    @Test
    void abriendoLaCajaConUnCodigoIncorrectoLanzaExcepcion() {
        CajaFuerte caja = new CajaFuerte("1234");

        RuntimeException error = assertThrows(RuntimeException.class, () -> caja.abrir("5678"));

        assertEquals("Código incorrecto", error.getMessage());
    }

    @Test
    void unaCajaAbriertaPermanececeAbiertaAntesDeSerCerrada() {
        CajaFuerte caja = new CajaFuerte("1234");
        caja.abrir("1234");

        assertTrue(caja.estaAbierta());
    }

    @Test
    void cerrandoUnaCajaAbriertaLaCierra() {
        CajaFuerte caja = new CajaFuerte("1234");
        caja.abrir("1234");

        caja.cerrar("5678");

        assertFalse(caja.estaAbierta());
    }

    @Test
    void cerrandoUnaCajaAbriertaActualizaElCodigo() {
        CajaFuerte caja = new CajaFuerte("1234");
        caja.abrir("1234");
        caja.cerrar("5678");

        caja.abrir("5678");

        assertTrue(caja.estaAbierta());
    }

    @Test
    void cerrandoUnaCajaCerradaLanzaExcepcion() {
        CajaFuerte caja = new CajaFuerte("1234");

        RuntimeException error = assertThrows(RuntimeException.class, () -> caja.cerrar("5678"));

        assertEquals("La caja fuerte no está abierta", error.getMessage());
    }

    @Test
    void noSePuedeAbrirUnaCajaConUnCodigoAntiguo() {
        CajaFuerte caja = new CajaFuerte("1234");
        caja.abrir("1234");
        caja.cerrar("5678");

        RuntimeException error = assertThrows(RuntimeException.class, () -> caja.abrir("1234"));

        assertEquals("Código incorrecto", error.getMessage());
    }

    @Test
    void dosCajasDistintasTienenCodigosIndependientes() {
        CajaFuerte caja1 = new CajaFuerte("1111");
        CajaFuerte caja2 = new CajaFuerte("2222");

        caja1.abrir("1111");
        caja2.abrir("2222");

        assertTrue(caja1.estaAbierta());
        assertTrue(caja2.estaAbierta());
    }

    @Test
    void seCanviarElCodigoVariasveces() {
        CajaFuerte caja = new CajaFuerte("1234");
        caja.abrir("1234");
        caja.cerrar("5678");

        caja.abrir("5678");
        caja.cerrar("9999");

        caja.abrir("9999");

        assertTrue(caja.estaAbierta());
    }
}
