package com.grupo6.ServiciosBarrioPrivado.Entidad;



import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Trabajo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    private Boolean finalizado;
    @ManyToOne
    private Usuario cliente;
    @ManyToOne
    private Proveedor proveedor;

    private CategoriaServicio categoria ;

    private String comentario;

    private Integer calificacion;


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public CategoriaServicio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaServicio categoria) {
        this.categoria = categoria;
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
}
