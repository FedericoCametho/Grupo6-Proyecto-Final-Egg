package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registro_usuario(@RequestParam String nombre, @RequestParam String apellido,@RequestParam String email, @RequestParam String password,
                                   @RequestParam String password2, @RequestParam String telefono, ModelMap modelo){
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

}
