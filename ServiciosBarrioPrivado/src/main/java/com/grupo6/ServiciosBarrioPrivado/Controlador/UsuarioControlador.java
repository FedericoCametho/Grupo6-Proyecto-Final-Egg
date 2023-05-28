package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

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
        return "modificar_usuario";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,  @RequestParam String nombre, @RequestParam String apellido,  @RequestParam String telefono, ModelMap modelo) throws MiException{
        try{
            usuarioServicio.modificar(id,nombre, apellido, telefono);
            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuarios", usuarios);
            return "usuario_lista";
        }catch(MiException ex){
            Usuario usuario = usuarioServicio.getUsuarioById(id);
            modelo.addAttribute("usuario", usuario);
            modelo.put("error", ex.getMessage());
            return "modificar_usuario";
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
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_lista";
    }

}
