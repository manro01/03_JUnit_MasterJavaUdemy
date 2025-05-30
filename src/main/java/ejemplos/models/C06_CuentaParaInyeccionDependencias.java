/*
 * Esta es la clase cuenta para probar las test con Inyección de Dependencias, 
 * Es lo mismo que estaba en C05_CuentaParaEtiquetas
 */
package ejemplos.models;

import ejemplos.exceptions.DineroInsuficienteException;
import java.math.BigDecimal;
import java.util.Objects;

public class C06_CuentaParaInyeccionDependencias
{
    private String persona;
    private BigDecimal saldo;
    
    private C06_BancoParaInyeccionDependencias banco;

    public String getPersona()
    {
        return persona;
    }

    public C06_CuentaParaInyeccionDependencias(String persona, BigDecimal saldo)
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

    public C06_BancoParaInyeccionDependencias getBanco()
    {
        return banco;
    }

    public void setBanco(C06_BancoParaInyeccionDependencias banco)
    {
        this.banco = banco;
    }
    
    
    
    public void debito(BigDecimal monto)
    {
        //this.saldo.subtract(monto); //aunque parece lógico esto NO ES CORRECTO porque BigDecimal es inmutable
        //this.saldo= this.saldo.subtract(monto); //esto es correcto hay que guardar el resultado ya sea en otra o en la misma variable
        
        //Se comentao la sentencia anterior, porque se alargo el método y ya no cumplia bien la función
        BigDecimal nuevoSaldo= this.saldo.subtract(monto);
        
        if(nuevoSaldo.compareTo(BigDecimal.ZERO)<0) //si esto se cumple el nuevo saldo es menor que cero y por lo tanto no se puede hacer la transacción
        {
            throw new DineroInsuficienteException("Dinero Insuficiente"); 
            //NOTA: este mensaje tiene que ser el mismo que se coloco en CuentaTest.testDineroInsuficienteException() para poder
            //  comparar si es el valor deseado
        }
        this.saldo= nuevoSaldo; //Si no hay problema el nuevoSaldo se guarda en el saldo
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
        final C06_CuentaParaInyeccionDependencias other = (C06_CuentaParaInyeccionDependencias) obj; //crea un objeto nuevo con lo mandado por parámetro
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

