@Service
public class TrabajoServicio implements UserDetailsService {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    @Transactional
    public void registrar(Proveedor proveedor, CategoriaServicio categoria, String comentario, Integer calificacion) throws MiException {

        this.validar(proveedor, categoria, comentario, calificacion);

        Trabajo trabajo = new Trabajo();
        Proveedor proveedor = new Proveedor();

        proveedor.setRol(Rol.PROVEEDOR);
        trabajo.setCategoriaServicio(categoria);
        trabajo.setComentario(comentario);
        trabajo.setCalificacion(calificacion);

        trabajoRepositorio.save(trabajo);
    }

    public List<Trabajo> listarTrabajo(){
        List<Trabajo> trabajos = new ArrayList();
        trabajos = trabajoRepositorio.findAll();
        return trabajos;
    }

    public Trabajo getTrabajoById(String id){
        return trabajoRepositorio.getOne(id);
    }

    public void validar(Proveedor proveedor, CategoriaServicio categoria, String comentario, Integer calificacion) throws MiException{

        if(proveedor.isEmpty() || proveedor == null){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if(categoria.toString().isEmpty() || categoria == null){
            throw new MiException("La categoria no puede ser nulo o estar vacio");
        }

        if(comentario.isEmpty() || comentario == null){
            throw new MiException("El comentario no puede ser nulo o estar vacio");
        }

        if(calificacion.isEmpty() || calificacion == null){
            throw new MiException("La calificacion no puede ser nulo o estar vacio");
        }
}