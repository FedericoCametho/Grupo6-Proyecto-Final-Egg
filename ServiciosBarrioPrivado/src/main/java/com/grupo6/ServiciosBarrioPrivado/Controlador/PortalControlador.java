package com.grupo6.ServiciosBarrioPrivado.Controlador;


import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

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
    public String afterLogin(HttpSession session, ModelMap modelo){
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
        modelo.addAttribute("usuario", session.getAttribute("usuariosesion"));
        return "inicio";
    }

    @GetMapping("/listarProveedores")
    public String listarTodos(ModelMap modelo){
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        return "proveedor_lista_guest";
    }

    @PostMapping("/listarPorCategoria")
    public String listarPorCategoria(@RequestParam String categoria, ModelMap modelo){
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);


        if (categoria.equals("Todos")){
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            return "proveedor_lista_guest";
        } else {
            List<Usuario> proveedores = proveedorServicio.listarPorCategoria(CategoriaServicio.valueOf(categoria));
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("categoriaSeleccionada", CategoriaServicio.valueOf(categoria));
            return "proveedor_lista_guest";
        }
    }

}