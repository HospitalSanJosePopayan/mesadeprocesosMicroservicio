package husjp.api.mesaprocesos.exceptionsControllers.exceptions;

public class EntidadSinAsignaciones extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String llaveMensaje;
    private final String codigo;

    public EntidadSinAsignaciones(CodigoError code){
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public EntidadSinAsignaciones(final String message){
        super(message);
        this.llaveMensaje = CodigoError.SIN_ASOCIACIONES.getLlaveMensaje();
        this.codigo = CodigoError.SIN_ASOCIACIONES.getCodigo();
    }
}
