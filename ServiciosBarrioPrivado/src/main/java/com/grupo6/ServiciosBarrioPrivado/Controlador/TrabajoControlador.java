package com.grupo6.ServiciosBarrioPrivado.Controlador;

import com.grupo6.ServiciosBarrioPrivado.Entidad.Trabajo;
import com.grupo6.ServiciosBarrioPrivado.Entidad.Usuario;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.CategoriaServicio;
import com.grupo6.ServiciosBarrioPrivado.Enumeracion.EstadoTrabajo;
import com.grupo6.ServiciosBarrioPrivado.Excepciones.MiException;
import com.grupo6.ServiciosBarrioPrivado.Servicio.ProveedorServicio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.TrabajoServicio;
import com.grupo6.ServiciosBarrioPrivado.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @Autowired
    private TrabajoServicio trabajoServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar/{idCliente}")
    public String registrar(@PathVariable String idCliente, ModelMap modelo){
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        Usuario cliente = usuarioServicio.getUsuarioById(idCliente);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("proveedores", proveedores);
        return "registro_trabajo";
    }


    @PostMapping("/registro")
    public String registroTrabajo(@RequestParam String fecha, @RequestParam String idCliente, @RequestParam String idProveedor,
                                  @RequestParam CategoriaServicio categoria, @RequestParam String detalles, ModelMap modelo){
        try{
            trabajoServicio.registrar(fecha, idCliente, idProveedor, categoria, detalles);
            return "inicio";

        } catch (MiException ex){
            modelo.put("error", ex.getMessage());

            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            List<Usuario> clientes = usuarioServicio.listarUsuarios();
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("proveedores", proveedores);

            return "registro_trabajo";
        }catch (ParseException e){
            modelo.put("error", e.getMessage());

            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            List<Usuario> clientes = usuarioServicio.listarUsuarios();
            modelo.addAttribute("clientes", clientes);
            modelo.addAttribute("proveedores", proveedores);
            return "registro_trabajo";
        }
    }

    @GetMapping("/registrarRapido/{idProveedor}/{idCliente}")
    public String registrarRapido(@PathVariable String idCliente, @PathVariable String idProveedor, ModelMap modelo){
        Usuario proveedor = proveedorServicio.getProveedorById(idProveedor);
        modelo.addAttribute("categoriaServicio", proveedor.getCategoriaServicio());
        modelo.addAttribute("proveedor", proveedor);
        Usuario cliente = usuarioServicio.getUsuarioById(idCliente);
        modelo.addAttribute("cliente", cliente);

        return "registroRapido_trabajo";
    }

    @GetMapping("/modificar/{id}")
    public String modificarTrabajo(@PathVariable String id, ModelMap modelo){
        Trabajo trabajo = trabajoServicio.getTrabajoById(id);
        modelo.addAttribute("trabajo", trabajo);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        List<Usuario> clientes = usuarioServicio.listarUsuarios();
        modelo.addAttribute("clientes", clientes);
        List<EstadoTrabajo> estados = Arrays.stream(EstadoTrabajo.values()).toList();
        modelo.addAttribute("estados", estados);
        return "modificar_trabajo";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam String fecha,@RequestParam String idCliente,
                            @RequestParam String idProveedor, @RequestParam CategoriaServicio categoria,
                            @RequestParam String detalles, ModelMap modelo) throws MiException, ParseException{
        try{
            trabajoServicio.modificar(id,fecha, idCliente, idProveedor, categoria, detalles);

            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            List<Usuario> clientes = usuarioServicio.listarUsuarios();
            modelo.addAttribute("clientes", clientes);
            List<Trabajo> trabajos = trabajoServicio.listarTrabajo();
            modelo.addAttribute("trabajos", trabajos);
            List<EstadoTrabajo> estados = Arrays.stream(EstadoTrabajo.values()).toList();
            modelo.addAttribute("estados", estados);
            return "trabajo_lista";

        }catch(MiException ex){
            Trabajo trabajo = trabajoServicio.getTrabajoById(id);
            modelo.addAttribute("trabajo", trabajo);

            List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
            modelo.addAttribute("categoriaServicio", categoriaServicio);
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);
            List<Usuario> clientes = usuarioServicio.listarUsuarios();
            modelo.addAttribute("clientes", clientes);
            List<EstadoTrabajo> estados = Arrays.stream(EstadoTrabajo.values()).toList();
            modelo.addAttribute("estados", estados);
            modelo.put("error", ex.getMessage());

            return "modificar_trabajo";
        }
    }

    @GetMapping("borrar/{id}")
    public String borrarTrabajo(@PathVariable String id, ModelMap modelo){
        Trabajo trabajo = trabajoServicio.getTrabajoById(id);
        modelo.addAttribute("trabajo", trabajo);
        return "trabajo_borrar";
    }

    @PostMapping("/confirmarBorrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo){
        try{
            trabajoServicio.eliminarTrabajo(id);
            return "redirect:../listar";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "inicio";
        }
    }


    @GetMapping("/confirmar/{id}")
    public String confirmarTrabajo(@PathVariable String id, ModelMap modelo){
        Trabajo trabajo = trabajoServicio.getTrabajoById(id);
        modelo.addAttribute("estados",Arrays.stream(EstadoTrabajo.values()).toList());
        modelo.addAttribute("trabajo", trabajo);
        return "trabajo_confirmacion";
    }

    @PostMapping("/confirmar/{id}")
    public String confirmarCambioEstado(@PathVariable String id, @RequestParam EstadoTrabajo estado, ModelMap modelo){
        try{
            if (estado.toString() == null || estado.toString().isEmpty()){
                throw new MiException("El estado a actualizar no puede ser nulo");
            }
            trabajoServicio.modificarEstado(id, estado);
            return "redirect:../listar";
        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "trabajo_confirmacion";
        }
    }

    @GetMapping("/listar")
    public String listarTodos(ModelMap modelo){
        List<Trabajo> trabajos = trabajoServicio.listarTrabajo();
        modelo.addAttribute("trabajos", trabajos);
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);

        return "trabajo_lista";
    }



    @PostMapping("/listarPorCategoria")
    public String filtrarPorCategoria(@RequestParam String categoria, ModelMap modelo) throws MiException{
        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);


        if (categoria.equals("Todos")){
            List<Trabajo> trabajos = trabajoServicio.listarTrabajo();
            modelo.addAttribute("trabajos", trabajos);
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);

            return "trabajo_lista";
        } else {
            List<Trabajo> trabajos = trabajoServicio.listarPorCategoria(CategoriaServicio.valueOf(categoria));
            modelo.addAttribute("trabajos", trabajos);
            modelo.addAttribute("categoriaSeleccionada", CategoriaServicio.valueOf(categoria));
            List<Usuario> proveedores = proveedorServicio.listarProveedores();
            modelo.addAttribute("proveedores", proveedores);

            return "trabajo_lista";
        }
    }

    @GetMapping("/listarPorUsuario/{idCliente}")
    public String listarPorCliente(@PathVariable String idCliente, ModelMap modelo) throws MiException{

        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);

        List<Trabajo> trabajos = trabajoServicio.listarPorUsuario(idCliente);
        modelo.addAttribute("trabajos", trabajos);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "trabajo_lista";

    }

    @PostMapping("/listarPorUsuario/{idCliente}")
    public String filtrarPorCliente(@PathVariable String idCliente, ModelMap modelo) throws MiException{

        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);

        List<Trabajo> trabajos = trabajoServicio.listarPorUsuario(idCliente);
        modelo.addAttribute("trabajos", trabajos);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "trabajo_lista";

    }


    @GetMapping("/listarPorProveedor/{idProveedor}")
    public String listarPorProveedor(@PathVariable String idProveedor, ModelMap modelo) throws MiException{

        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);

        List<Trabajo> trabajos = trabajoServicio.listarPorProveedor(idProveedor);
        modelo.addAttribute("trabajos", trabajos);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "trabajo_lista";

    }

    @PostMapping("/listarPorProveedor/{idProveedor}")
    public String filtrarPorProveedor(@PathVariable String idProveedor, ModelMap modelo) throws MiException{

        List<CategoriaServicio> categoriaServicio = Arrays.stream(CategoriaServicio.values()).toList();
        modelo.addAttribute("categoriaServicio", categoriaServicio);



        List<Trabajo> trabajos = trabajoServicio.listarPorProveedor(idProveedor);
        modelo.addAttribute("trabajos", trabajos);
        List<Usuario> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "trabajo_lista";
    }





}
