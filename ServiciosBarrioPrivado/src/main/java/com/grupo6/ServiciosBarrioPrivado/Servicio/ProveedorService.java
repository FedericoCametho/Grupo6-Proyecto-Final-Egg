package com.grupo6.ServiciosBarrioPrivado.Servicio;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Proveedor;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.Rol;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.ProveedorRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class ProveedorService implements UserDetailsService {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String password, String password2, String telefono, CategoriaServicio categoria) throws MiException {
        this.validar(nombre,email,password,password2, telefono, categoria);

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);

        proveedorRepositorio.save(proveedor);
    }




    public void validar(String nombre, String email, String password, String password2, String telefono, CategoriaServicio categoria) throws MiException{

        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
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

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);

        if (proveedor != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+proveedor.getRol().toString());
            permisos.add(p);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
