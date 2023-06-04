package com.grupo6.ServiciosBarrioPrivado.Servicio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.CategoriaServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicioService {

    @Autowired
    private CategoriaServicioRepositorio categoriaServicioRepositorio;

    @Transactional
    public void crear(String nombre, String descripcion)throws MiException{
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre de la categoria de serivcio no puede ser nulo");
        }
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre(nombre.trim().toUpperCase());
        categoria.setDescripcion(descripcion.trim());
        categoriaServicioRepositorio.save(categoria);
    }

    @Transactional
    public void modificar(String id, String nombre, String descripcion)throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id de la categoria de serivcio no puede ser nulo");
        }
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre de la categoria de serivcio no puede ser nulo o estar vacio");
        }
        Optional<CategoriaServicio> respuesta = categoriaServicioRepositorio.findById(id);
        if (respuesta.isPresent()){
            CategoriaServicio categoria = respuesta.get();
            categoria.setNombre(nombre.trim().toUpperCase());
            categoria.setDescripcion(descripcion.trim());
            categoriaServicioRepositorio.save(categoria);
        }

    }

    @Transactional
    public void eliminarCategoria(String id)  throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id de la categoria de serivcio no puede ser nulo");
        }
        CategoriaServicio categoria = categoriaServicioRepositorio.findById(id).get();
        categoriaServicioRepositorio.delete(categoria);
    }


    public List<CategoriaServicio> listarTodas(){
        return categoriaServicioRepositorio.findAll();
    }

    public CategoriaServicio getCategoriaById(String id){
        return categoriaServicioRepositorio.findById(id).get();
    }

    public List<Usuario> trabajadoresDeUnaCategoria(String id){
        
    }

}
