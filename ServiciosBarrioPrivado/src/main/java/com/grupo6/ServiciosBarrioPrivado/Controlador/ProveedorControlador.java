package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.ProveedorServicio;

import com.grupo6.ServiciosBarrioPrivado.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;


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

    @GetMapping("/modificar/{id}")
    public String modificarProveedor(@PathVariable String id, ModelMap modelo){
        Usuario proveedor = proveedorServicio.getProveedorById(id);
        modelo.addAttribute("proveedor", proveedor);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        return "modificar_proveedor";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,  @RequestParam String nombre, @RequestParam String apellido,
                            @RequestParam String telefono,@RequestParam CategoriaServicio categoria,
                            @RequestParam Integer precioPorHora, ModelMap modelo) throws MiException{
        try{
            proveedorServicio.modificar(id,nombre, apellido, telefono, categoria, precioPorHora);
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            return "proveedor_lista";
        }catch(MiException ex){
            Usuario proveedor = proveedorServicio.getProveedorById(id);
            modelo.addAttribute("proveedor", proveedor);
            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            modelo.put("error", ex.getMessage());
            return "modificar_proveedor";
        }
    }

    @GetMapping("borrar/{id}")
    public String borrarProveedor(@PathVariable String id, ModelMap modelo){
        Usuario proveedor = proveedorServicio.getProveedorById(id);
        modelo.addAttribute("proveedor", proveedor);
        return "proveedor_borrar";
    }

    @PostMapping("/confirmarBorrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) throws MiException{
        try{
            if (proveedorServicio.trabajosDeUnProveedor(id).isEmpty()){
                proveedorServicio.eliminarProveedor(id);
                return "redirect:../listar";
            }
            modelo.put("error", "El Proveedor que desea borrar de la base de datos, Posee Trabajos asociados. Debera primero eliminar los trabajos asociados a dicho proveedor para luego eliminarlo");
            return "inicio";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "inicio";
        }
    }


    @GetMapping("/modificarPerfilProveedor/{id}")
    public String modificarPerfilProveedor(@PathVariable String id, ModelMap modelo){
        Usuario proveedor = proveedorServicio.getProveedorById(id);
        modelo.addAttribute("proveedor", proveedor);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        try{
            List<Trabajo> trabajos = proveedorServicio.trabajosDeUnProveedor(id);
            modelo.addAttribute("trabajos", trabajos);
            return "modificar_perfil_proveedor";
        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "modificar_perfil_proveedor";
        }

    }


    @PostMapping("/modificarPerfilProveedor/{id}")
    public String modificarPerfilP(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
                                   @RequestParam String telefono, @RequestParam CategoriaServicio categoria,
                                   @RequestParam Integer precioPorHora,
                                   ModelMap modelo) {
        try{
            proveedorServicio.modificarPerfil(id,nombre, apellido, telefono, categoria, precioPorHora);
            modelo.addAttribute("usuario", proveedorServicio.getProveedorById(id));
            return "inicio";
        }catch(MiException ex){
            Usuario proveedor = proveedorServicio.getProveedorById(id);
            modelo.addAttribute("proveedor", proveedor);
            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            modelo.put("error", ex.getMessage());
            return "modificar_perfil_proveedor";
        }
    }




    @GetMapping("/listar")
    public String listarTodos(ModelMap modelo){
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        modelo.addAttribute("provServicio", proveedorServicio);
        return "proveedor_lista";
    }

    @PostMapping("/listarPorCategoria")
    public String listarPorCategoria(@RequestParam String categoria, ModelMap modelo){
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);


        if (categoria.equals("Todos")){
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("provServicio", proveedorServicio);
            return "proveedor_lista";
        } else {
            List<Usuario> proveedores = proveedorServicio.listarPorCategoria(CategoriaServicio.valueOf(categoria));
            modelo.addAttribute("proveedores", proveedores);
            modelo.addAttribute("categoriaSeleccionada", CategoriaServicio.valueOf(categoria));
            modelo.addAttribute("provServicio", proveedorServicio);
            return "proveedor_lista";
        }
    }

    @GetMapping("/cambiarRolAUsuario/{idProveedor}")
    public String confirmacionCambiarRol(@PathVariable String idProveedor, ModelMap modelo){
        Usuario proveedor = proveedorServicio.getProveedorById(idProveedor);
        modelo.addAttribute("proveedor", proveedor);
        return "cambio_rol_a_usuario";
    }

    @PostMapping("/cambiarRolAUsuario/{idProveedor}")
    public String cambiarRol(@PathVariable String idProveedor,  @RequestParam String nombre, @RequestParam String apellido,
                         @RequestParam String telefono, ModelMap modelo){

        try{
            usuarioServicio.cambiarARolUsuario(idProveedor,nombre,apellido,telefono);
        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
        } finally {
            Usuario usuario = usuarioServicio.getUsuarioById(idProveedor);
            modelo.addAttribute("usuario", usuario);
            return "perfil_rol_logout";
        }


    }



}
