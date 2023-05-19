package com.grupo6.ServiciosBarrioPrivado.Repositorio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Proveedor;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    @Query("SELECT p FROM Trabajo p WHERE p.proveedor = :proveedor")
    public Proveedor buscarPorProveedor(@Param("proveedor") String proveedor);


    @Query("SELECT p FROM Trabajo p WHERE p.categoriaServicio =: categoria")
    public List<Proveedor> buscarPorCategoria(@Param("categoria") String categoria);

}
