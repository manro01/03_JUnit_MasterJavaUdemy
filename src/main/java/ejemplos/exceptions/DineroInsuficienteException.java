/*
 * Excepcion para cuando no hay fondos suficientes en la cuenta
 */
package ejemplos.exceptions;

public class DineroInsuficienteException extends RuntimeException
{

    public DineroInsuficienteException(String message)
    {
        super(message);
    }
    
}
