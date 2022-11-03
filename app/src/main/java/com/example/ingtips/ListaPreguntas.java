package com.example.ingtips;

import java.io.Serializable;

public class ListaPreguntas implements Serializable {

    private final String pregunta, opcion1, opcion2, opcion3, opcion4;
    private final int respuesta;
    private int respuestaSeleccionada;

    public ListaPreguntas(String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, int respuesta) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.respuesta = respuesta;
        this.respuestaSeleccionada = 0;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public int getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    public void setRespuestaSeleccionada(int respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }
}

