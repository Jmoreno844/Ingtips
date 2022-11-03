package com.example.ingtips;

public class Seleccionador {
    private String Seleccion;
    private String imagen;


    public Seleccionador(String seleccion, String imagen) {
        this.Seleccion = seleccion;
        this.imagen = imagen;
    }

    public String getSeleccion() {
        return Seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.Seleccion = seleccion;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
