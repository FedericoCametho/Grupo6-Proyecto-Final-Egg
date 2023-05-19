package com.grupo6.ServiciosBarrioPrivado.Controlador;


import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.TrabajoServicio;
import com.sun.istack.logging.Logger;
import java.util.logging.Level;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @GetMapping("/trabajo")
    public String trabajo(){
        return "trabajo.html";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    }

    @GetMapping("/registro")
    public String registro(@RequestParam String trabajo_registro){
        /*try {
            TrabajoServicio.registroTrabajo(trabajo_registro);
        } 
        catch {(MiException ex) {
            Logger.getLogger(TrabajoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "registrar.html";
        }
        return "index.html";
        }*/
        return "index.html";
    }
}
