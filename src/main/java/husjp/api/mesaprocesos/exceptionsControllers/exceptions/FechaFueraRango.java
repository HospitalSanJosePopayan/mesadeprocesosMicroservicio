package husjp.api.mesaprocesos.exceptionsControllers.exceptions;

public class FechaFueraRango extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String llaveMensaje;
    private final String codigo;

    public FechaFueraRango(CodigoError code){
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

    public FechaFueraRango(final String message){
        super(message);
        this.llaveMensaje = CodigoError.FECHA_FUERA_RANGO.getLlaveMensaje();
        this.codigo = CodigoError.FECHA_FUERA_RANGO.getCodigo();
    }
}
