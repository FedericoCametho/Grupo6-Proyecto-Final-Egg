package com.grupo6.ServiciosBarrioPrivado.Repositorio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {


}
