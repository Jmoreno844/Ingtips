package com.example.ingtips;

public class Videos {
    private String titulo;
    private String urlImagen;
    private String imagen;

    public Videos(String titulo, String urlImagen, String imagen) {
        this.titulo = titulo;
        this.urlImagen = urlImagen;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
