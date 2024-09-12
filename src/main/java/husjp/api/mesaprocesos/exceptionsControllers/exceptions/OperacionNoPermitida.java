package husjp.api.mesaprocesos.exceptionsControllers.exceptions;

public class OperacionNoPermitida extends  RuntimeException{
    private static final long serialVersionUID = 1L;
    private final String llaveMensaje;
    private final String codigo;

    public OperacionNoPermitida(CodigoError code){
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public OperacionNoPermitida(final String message){
        super(message);
        this.llaveMensaje = CodigoError.ENTIDAD_NO_ENCONTRADA.getLlaveMensaje();
        this.codigo = CodigoError.ENTIDAD_NO_ENCONTRADA.getCodigo();
    }
}
