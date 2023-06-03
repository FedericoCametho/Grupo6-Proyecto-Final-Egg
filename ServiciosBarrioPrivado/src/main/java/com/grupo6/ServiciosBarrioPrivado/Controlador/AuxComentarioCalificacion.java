package com.grupo6.ServiciosBarrioPrivado.Controlador;

public class AuxComentarioCalificacion {
    private String id;
    private String comentario;
    private Integer calificacion;

    public AuxComentarioCalificacion(String id, String comentario, Integer calificacion){
        this.id = id;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
    public String getId() {
        return id;
    }
}
