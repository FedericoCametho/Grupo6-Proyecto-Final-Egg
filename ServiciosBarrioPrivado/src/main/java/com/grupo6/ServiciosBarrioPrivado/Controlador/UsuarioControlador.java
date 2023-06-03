package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.Rol;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Repositorio.TrabajoRepositorio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.ProveedorServicio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.TrabajoServicio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private TrabajoServicio trabajoServicio;

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro_usuario";
    }


    @PostMapping("/registro")
    public String registro_usuario(@RequestParam String nombre, @RequestParam String apellido,@RequestParam String email,  @RequestParam String telefono, @RequestParam String password,
                                   @RequestParam String password2, ModelMap modelo){
        try{
            usuarioServicio.registrar(nombre, apellido, email, password, password2, telefono);
            return "index";

        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("telefono", telefono);

            return "registro_usuario";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap modelo){
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        modelo.addAttribute("usuario", usuario);
        List<Rol> roles = Arrays.stream(Rol.values()).toList();
        modelo.addAttribute("roles", roles);
        return "modificar_usuario";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam Rol rol, ModelMap modelo) throws MiException{
        try{
            usuarioServicio.modificar(id, nombre, apellido, telefono, rol);
            List<Usuario> usuarios = usuarioServicio.listarUsuarios().stream().filter(u -> !(u.getRol().toString().equals("PROVEEDOR"))).collect(Collectors.toList());
            modelo.addAttribute("usuarios", usuarios);
            List<Rol> roles = Arrays.stream(Rol.values()).toList();
            modelo.addAttribute("roles", roles);
            return "usuario_lista";
        }catch(MiException ex){
            Usuario usuario = usuarioServicio.getUsuarioById(id);
            modelo.addAttribute("usuario", usuario);
            List<Rol> roles = Arrays.stream(Rol.values()).toList();
            modelo.addAttribute("roles", roles);
            modelo.put("error", ex.getMessage());
            return "modificar_usuario";
        }
    }

    @GetMapping("/modificarPerfilUsuario/{id}")
    public String modificarPerfilUsuario(@PathVariable String id, ModelMap modelo){
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        modelo.addAttribute("usuario", usuario);
        List<Rol> roles = Arrays.stream(Rol.values()).filter(r -> !r.equals(Rol.ADMIN)).collect(Collectors.toList());
        modelo.addAttribute("roles", roles);
        return "modificar_perfil_usuario";
    }
    
    @PostMapping("/modificarPerfilUsuario/{id}")
    public String modificarPerfilU(@PathVariable String id,  @RequestParam String nombre, @RequestParam String apellido,
                                   @RequestParam String telefono, @RequestParam Rol rol,
                                   ModelMap modelo) {
        try{
            usuarioServicio.modificarPerfil(id, nombre, apellido, telefono, rol);
            modelo.addAttribute("usuario", usuarioServicio.getUsuarioById(id));
            if (rol.toString().equals("USER")){
                List<Rol> roles = Arrays.stream(Rol.values()).filter(r -> !r.equals(Rol.ADMIN)).collect(Collectors.toList());
                modelo.addAttribute("roles", roles);
            }
            return "redirect:/inicio";
        }catch(MiException ex){
            Usuario usuario = usuarioServicio.getUsuarioById(id);
            modelo.addAttribute("usuario", usuario);
            modelo.put("error", ex.getMessage());
            return "modificar_perfil_usuario";
        }
    }


    @GetMapping("borrar/{id}")
    public String borrarUsuario(@PathVariable String id, ModelMap modelo){
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        modelo.addAttribute("usuario", usuario);
        return "usuario_borrar";
    }

    @PostMapping("/confirmarBorrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) throws MiException{
        try{
            if (usuarioServicio.trabajosDeUnUsuario(id).isEmpty()){
                usuarioServicio.eliminarUsuario(id);
                return "redirect:../listar";
            }
            modelo.put("error", "El Usuario que desea borrar de la base de datos, Posee Trabajos asociados. Debera primero eliminar los trabajos asociados a dicho usuario para luego eliminarlo");
            return "inicio";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "inicio";
        }
    }


    @GetMapping("/listar")
    public String listarTodos(ModelMap modelo){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios().stream().filter(u -> !(u.getRol().toString().equals("PROVEEDOR"))).collect(Collectors.toList());
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_lista";
    }

    @GetMapping("/perfil/{id}/{rol}")
    public String perfil(@PathVariable Rol rol, @PathVariable String id, ModelMap modelo){
        Usuario usuario;
        if (rol.toString().equals("PROVEEDOR")){
            usuario = proveedorServicio.getProveedorById(id);
            modelo.addAttribute("usuario", usuario);

            try{
                List<AuxComentarioCalificacion> resultados = trabajoServicio.listarPorProveedor(id).stream().map(t -> new AuxComentarioCalificacion(t.getId(), t.getComentario(), t.getCalificacion())).collect(Collectors.toList());
                if (resultados.isEmpty()) {
                    resultados.add(new AuxComentarioCalificacion("", "Sin Comentarios", 0));
                }
                modelo.addAttribute("resultados", resultados);
            } catch(MiException ex){
                modelo.put("error", ex.getMessage());
            }

        } else {
            usuario = usuarioServicio.getUsuarioById(id);
            modelo.addAttribute("usuario", usuario);
        }

        return "perfil";
    }
    
    @GetMapping("borrarComentarioTrabajo/{id}")
    public String borrarComentarioT(@PathVariable String id, ModelMap modelo){
        Trabajo trabajo = trabajoServicio.getTrabajoById(id);
        modelo.addAttribute("trabajo", trabajo);
        return "trabajo_borrar_comentario";
    }

    @PostMapping("/confirmarBorrarComentarioProveedor/{id}")
    public String borrarComentario(@PathVariable String id, ModelMap modelo) throws MiException{
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        Trabajo trabajo = respuesta.get();
        
        trabajo.setComentario(null);
        
        trabajoRepositorio.save(trabajo);
        
        return "redirect:/proveedor/listar";
    }
    
}