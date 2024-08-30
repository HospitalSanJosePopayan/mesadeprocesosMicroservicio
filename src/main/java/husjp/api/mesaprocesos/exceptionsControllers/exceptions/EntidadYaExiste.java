package husjp.api.mesaprocesos.exceptionsControllers.exceptions;

public class EntidadYaExiste  extends  RuntimeException{
    private static final long serialVersionUID = 1L;
    private final String llaveMensaje;
    private final String codigo;

    public EntidadYaExiste(CodigoError code){
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public EntidadYaExiste(final String message){
        super(message);
        this.llaveMensaje = CodigoError.ENTIDAD_YA_EXISTE.getLlaveMensaje();
        this.codigo = CodigoError.ENTIDAD_YA_EXISTE.getCodigo();
    }
}
