package com.grupo6.ServiciosBarrioPrivado.Servicio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.Rol;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;

import com.grupo6.ServiciosBarrioPrivado.Repositorio.TrabajoRepositorio;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorServicio {

    @Autowired
    private UsuarioRepositorio proveedorRepositorio;

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String password2, String telefono,
                          CategoriaServicio categoria, Integer precioPorHora) throws MiException {

        this.validar(nombre,apellido, email,password,password2, telefono, categoria, precioPorHora);

        Usuario proveedor = new Usuario();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setEmail(email);
        proveedor.setTelefono(telefono);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setCategoriaServicio(categoria);
        proveedor.setPrecioPorHora(precioPorHora);


        proveedorRepositorio.save(proveedor);
    }


    @Transactional
    public void modificar(String id, String nombre, String apellido, String telefono,
                          CategoriaServicio categoria, Integer precioPorHora, Rol rol) throws MiException{
        this.validarParcial(nombre,apellido,telefono, categoria, precioPorHora);

        Optional<Usuario> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()){
            Usuario proveedor = respuesta.get();

            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setTelefono(telefono);
            proveedor.setCategoriaServicio(categoria);
            proveedor.setPrecioPorHora(precioPorHora);
            proveedor.setRol(rol);
            
            if (rol == Rol.USER || rol == Rol.ADMIN){
                proveedor.setCategoriaServicio(null);
                proveedor.setPrecioPorHora(0);
            }

            proveedorRepositorio.save(proveedor);
        }

    }

    @Transactional
    public void eliminarProveedor(String id)throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = proveedorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario proveedor = respuesta.get();
            proveedorRepositorio.delete(proveedor);
        }
    }

    @Transactional
    public void eliminarComentario(String id)throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = proveedorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario proveedor = respuesta.get();
            proveedor.setCalificacion(null);
            
            proveedorRepositorio.save(proveedor);
        }
    }
    
    @Transactional
    public void modificarPerfil(String id, String nombre,String apellido, String telefono,
                                CategoriaServicio categoria, Integer precioPorHora) throws MiException{
        this.validarParcial(nombre, apellido, telefono,categoria, precioPorHora);
        Optional<Usuario> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()){
            Usuario proveedor = respuesta.get();

            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setTelefono(telefono);
            proveedor.setCategoriaServicio(categoria);
            proveedor.setPrecioPorHora(precioPorHora);
            
            proveedorRepositorio.save(proveedor);
        }

    }

    // METODOS DE CONSULTA

    public List<Usuario> listarProveedores(){
        List<Usuario> proveedores = new ArrayList();
        proveedores = proveedorRepositorio.findAll().stream().filter( u -> u.getRol().toString().equals("PROVEEDOR")).collect(Collectors.toList());
        return proveedores;
    }

    public List<Usuario> listarPorCategoria(CategoriaServicio categoria){
        List<Usuario> proveedores = new ArrayList();
        proveedores = proveedorRepositorio.buscarPorCategoria(categoria);
        return proveedores;
    }

    public Usuario getProveedorById(String id){
        return proveedorRepositorio.getOne(id);
    }



    public List<Trabajo> trabajosDeUnProveedor(String idProveedor) throws MiException{
        return trabajoRepositorio.buscarPorProveedor(idProveedor);
    }

    public void validar(String nombre, String apellido, String email, String password, String password2, String telefono,
                        CategoriaServicio categoria, Integer precioPorHora) throws MiException{

        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }

        if(email.isEmpty() || email == null){
            throw new MiException("El email no puede ser nulo o estar vacio");
        }

        if(password.isEmpty() || password == null || password.length() < 6){
            throw new MiException("La contraseña no puede ser nula o estar vacia y debe tener mas de 5 digitos");
        }

        if (!password.equals(password2)){
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

        if(telefono.isEmpty() || telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        if(categoria.toString().isEmpty() || categoria == null){
            throw new MiException("La categoria no puede ser nulo o estar vacio");
        }
        if(precioPorHora == null){
            throw new MiException("El precio por hora no puede ser nulo");
        }

    }

    public void validarParcial( String nombre, String apellido, String telefono,
                        CategoriaServicio categoria, Integer precioPorHora) throws MiException{

        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }

        if(telefono.isEmpty() || telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        if(categoria.toString().isEmpty() || categoria == null){
            throw new MiException("La categoria no puede ser nulo o estar vacio");
        }
        if(precioPorHora == null){
            throw new MiException("El precio por hora no puede ser nulo");
        }

    }

    public void validarPerfil(String nombre, String apellido, String telefono,CategoriaServicio categoria,
                              Integer precioPorHora, String password, String password2) throws MiException{

        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if(apellido.isEmpty() || apellido == null){
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }

        if(password.isEmpty() || password == null || password.length() < 6){
            throw new MiException("La contraseña no puede ser nula o estar vacia y debe tener mas de 5 digitos");
        }

        if (!password.equals(password2)){
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

        if(telefono.isEmpty() || telefono == null){
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        if(categoria.toString().isEmpty() || categoria == null){
            throw new MiException("La categoria no puede ser nulo o estar vacio");
        }
        if(precioPorHora == null){
            throw new MiException("El precio por hora no puede ser nulo");
        }

    }

}
