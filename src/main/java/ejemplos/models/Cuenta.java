/**
 * Representa una cuenta de banco simple
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta
{
    private String persona;
    private BigDecimal saldo;

    public String getPersona()
    {
        return persona;
    }

    public Cuenta(String persona, BigDecimal saldo)
    {
        this.saldo = saldo;
        this.persona= persona;
    }
    
    

    public void setPersona(String persona)
    {
        this.persona = persona;
    }

    public BigDecimal getSaldo()
    {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo)
    {
        this.saldo = saldo;
    }
    
    public void debito(BigDecimal monto)
    {
        //this.saldo.subtract(monto); //aunque parece lógico esto NO ES CORRECTO porque BigDecimal es inmutable
        this.saldo= this.saldo.subtract(monto);
    }
    
    public void credito(BigDecimal monto)
    {
        //this.saldo.add(monto); //aunque parece lógico esto NO ES CORRECTO porque BigDecimal es inmutable
        this.saldo= this.saldo.add(monto);
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { //revisa si las referencias son las mismas
            return true;
        }
        if (obj == null) { // revisa que el objeto a comparar sea diferente de nulo
            return false;
        }
        if (getClass() != obj.getClass()) { //revisa que los objetos sean de la misma clase
            return false;
        }
        final Cuenta other = (Cuenta) obj; //crea un objeto nuevo con lo mandado por parámetro
        if (!Objects.equals(this.persona, other.persona)) { //verifica si el valor persona es el mismo
            return false;
        }
        return Objects.equals(this.saldo, other.saldo); //revisa si el  el saldo es el mismo
        
        //Este método funciona con "corto circuito" es decir si cuando se cumpla una condición se terminan
        //  las pruebas siguientes no se realizan, de lo contrario se continua, hasta
        //  el final, donde se regresa el valor de la útlima prueba sin importat 
        //  cual sea el resultado
    }
    
    
}
