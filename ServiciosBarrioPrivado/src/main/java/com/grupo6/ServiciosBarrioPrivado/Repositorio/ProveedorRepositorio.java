package com.grupo6.ServiciosBarrioPrivado.Repositorio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Proveedor;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio  extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM Proveedor p WHERE p.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);

}
