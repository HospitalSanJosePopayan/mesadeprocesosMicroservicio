package husjp.api.mesaprocesos.exceptionsControllers.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CodigoError {

    ERROR_GENERICO("GC-0001", "ERROR GENERICO"),
    ENTIDAD_YA_EXISTE("GC-0002", "ERROR ENTIDAD YA EXISTE"),
    ENTIDAD_NO_ENCONTRADA("GC-0003", "Entidad no encontrada"),
    VIOLACION_REGLA_DE_NEGOCIO("GC-0004", "Regla de negocio violada"),
    CREDENCIALES_INVALIDAS("GC-0005", "Error al iniciar sesión, compruebe sus credenciales y vuelva a intentarlo"),
    USUARIO_DESHABILITADO("GC-0006", "El usuario no ha sido verificado, por favor revise su correo para verificar su cuenta"),
    CAMPO_NO_ENCONTRADO("GC-0007","El campo buscado esta vacio"),
    CORREO_NO_ENVIADO("GC-0008","No se pudo enviar el correo electronico."),
    NO_HAY_COINCIDENCIA("GC-0009", "No hay coincidencia"),
    CONEXION_NO_ESTABLECIDA("GC-0010","Error en la conexión con el servidor. "),
    ACCESO_DENEGADOO("GC-0011","Acceso denegado. "),
    SIN_CREDENCIALES("GC-0012","No se encontraron las credenciales para acceder a esta funcion. "),
    TOKEN_EXPIRO("GC-0012","EL TOKEN HA EXPIRADO"),
    INCUMPLE_CONDICIONES("GC-0013","INCUMPLE CONDICIÓN"),
    SIN_ASOCIACIONES("GC-0014","No tiene Asignaciones");

    private final String codigo;
    private final String llaveMensaje;

}
