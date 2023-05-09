package com.grupo6.ServiciosBarrioPrivado.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {


    @GetMapping("/login")        // localhost:8080/
    public String index() {
        return "index";
    }

}