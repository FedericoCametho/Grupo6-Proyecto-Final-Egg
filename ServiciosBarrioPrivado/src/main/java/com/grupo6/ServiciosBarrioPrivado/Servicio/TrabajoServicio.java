package com.grupo6.ServiciosBarrioPrivado.Servicio;



import com.grupo6.ServiciosBarrioPrivado.Entidad.Proveedor;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.Rol;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.ProveedorRepositorio;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.TrabajoRepositorio;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Transactional
    public void registrar(Date fecha, String idCliente, String idProveedor, CategoriaServicio categoria,
                          String detalles) throws MiException {

        this.validar(fecha, idCliente, idProveedor, categoria);

        Usuario cliente = usuarioServicio.getUsuarioById(idCliente);
        Proveedor proveedor = proveedorServicio.getProveedorById(idProveedor);

        Trabajo trabajo = new Trabajo();

        trabajo.setFecha(fecha);
        trabajo.setCliente(cliente);
        trabajo.setProveedor(proveedor);
        trabajo.setCategoria(categoria);
        trabajo.setDetalles(detalles);
        trabajo.setComentario("");
        trabajo.setCalificacion(5);
        trabajo.setFinalizado(false);

        trabajoRepositorio.save(trabajo);
    }



    // MODIFICAR ENTIDAD DE LIBRO DE BIBLIOTECA, COMO REFERENCIA PARA IR A BUSCAR EN NUESTRA ENTIDAD TRABAJO
    // EL CLIENTE Y EL PROVEEDOR

    /*
    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        this.validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);
        }

    }



     */



    // METODOS DE CONSULTA Y LISTADO


    public List<Trabajo> listarTrabajo() {
        List<Trabajo> trabajos = new ArrayList();
        trabajos = trabajoRepositorio.findAll();
        return trabajos;
    }

    public Trabajo getTrabajoById(String id) {
        return trabajoRepositorio.getOne(id);
    }

    public void validar(Date fecha,String idCliente,String idProveedor,CategoriaServicio categoria) throws MiException {

        if (fecha == null) {
            throw new MiException("La fecha no puede ser nula");
        }

        if (categoria.toString().isEmpty() || categoria == null) {
            throw new MiException("La categoria no puede ser nulo o estar vacio");
        }

        if (idCliente.isEmpty() || idCliente == null) {
            throw new MiException("El comentario no puede ser nulo o estar vacio");
        }

        if (idProveedor.isEmpty() || idProveedor == null) {
            throw new MiException("La calificacion no puede ser nulo o estar vacio");
        }
    }


}