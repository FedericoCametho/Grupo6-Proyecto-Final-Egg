package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;


    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        return "registro_proveedor";
    }


    @PostMapping("/registro")
    public String registro_proveedor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String password,
                                     @RequestParam String password2, @RequestParam String telefono,
                                     @RequestParam CategoriaServicio categoria, @RequestParam Integer precioPorHora,
                                     ModelMap modelo){
        try{
            proveedorServicio.registrar(nombre, apellido, email, password, password2, telefono, categoria, precioPorHora);
            return "index";

        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("telefono", telefono);
            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            modelo.put("precioPorhora", precioPorHora);

            return "registro_proveedor";
        }
    }




}
