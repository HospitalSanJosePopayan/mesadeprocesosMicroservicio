package husjp.api.mesaprocesos.exceptionsControllers;

import husjp.api.mesaprocesos.exceptionsControllers.exceptions.EntidadNoExisteException;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.*;
import husjp.api.mesaprocesos.exceptionsControllers.exceptions.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class RestApiExceptionHandler {

    //crear error generico automaticamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(final HttpServletRequest req, final Exception ex, final Locale locale) {
        final Error error = ErrorUtils
                .crearError(CodigoError.ERROR_GENERICO.getCodigo(),
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //crea la excepcion de entidadnoexiste cuando usamos el new throw new ...
    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleGenericException(final HttpServletRequest req, final EntidadNoExisteException ex, final Locale locale){
        final Error error = ErrorUtils.crearError(CodigoError.ENTIDAD_NO_ENCONTRADA.getCodigo(), String.format("%s, %s", CodigoError.ENTIDAD_NO_ENCONTRADA.getLlaveMensaje(), ex.getMessage()), HttpStatus.NOT_FOUND.value()).setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //este metodo se ejecuta automaticamente con el valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensajeDeError = error.getDefaultMessage();
            errores.put(campo, mensajeDeError);
                }
        );
        return new ResponseEntity<Map<String,String>>(errores, HttpStatus.BAD_REQUEST);
    }
}
