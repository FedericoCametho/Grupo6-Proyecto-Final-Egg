package com.grupo6.ServiciosBarrioPrivado.Repositorio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {


    @Query("SELECT t FROM Trabajo t WHERE t.cliente.id = :idUsuario")
    public List<Trabajo> buscarPorUsuario(@Param("idUsuario") String idUsuario);

    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :idProveedor")
    public List<Trabajo> buscarPorProveedor(@Param("idProveedor") String idProveedor);


    @Query("SELECT t FROM Trabajo t WHERE t.categoria = :categoria")
    public List<Trabajo> buscarPorCategoria(@Param("categoria") CategoriaServicio categoria);

}
