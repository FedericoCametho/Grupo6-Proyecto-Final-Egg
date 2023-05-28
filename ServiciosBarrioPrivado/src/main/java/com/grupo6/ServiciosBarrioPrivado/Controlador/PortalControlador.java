package com.grupo6.ServiciosBarrioPrivado.Controlador;


import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {


    @GetMapping("/")        // localhost:8080/
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginBifurcation(){
        return "login_bifurcation";
    }

    @GetMapping("/registrar")
    public String registroBifurcation(){
        return "registro_bifurcation";
    }

//    @GetMapping("/login-proveedor")
//    public String loginProveedor(@RequestParam(required = false) String error, ModelMap modelo){
//        if (error != null){
//            modelo.put("error", "Usuario o contraseña invalida");
//        }
//        return "login_proveedor";
//    }

    @GetMapping("/login-usuario-proveedor")
    public String loginUsuario(@RequestParam(required = false) String error, ModelMap modelo){
        if (error != null){
            modelo.put("error", "Usuario o contraseña invalida");
        }
        return "login_usuario";
    }

    @PostMapping("/logincheck")
    public String loginCheck(){
        return "index";
    }



    @GetMapping("/inicio")
    public String afterLogin(){
//        Usuario loggedUser = (Usuario) session.getAttribute("userSession");
//
//        if (loggedUser.getRol().toString().equals("ADMIN")) {
//            return "redirect:/admin/dashboard";
//        }
//
//        if (loggedUser.getRol().toString().equals("PROVEEDOR")) {
//            return "redirect:/proveedor/inicio";
//        }
//
//        if (loggedUser.getRol().toString().equals("USUARIO")) {
//            return "redirect:/usuario/inicio";
//        }

        return "inicio";
    }

}