/**
 * Es lo mismo que Cuenta pero la vamos a usar para demostrar el uso
 * de innerclass ( es decir prueba anidadas), como implicaba modificar varias cosas
 * de la clase Cuenta y como el archivo de test ESTA LIGADO a su clase para 
 * probar por el nombre, decidi hacer esta otra para ejemplificar
 */
package ejemplos.models;

import ejemplos.exceptions.DineroInsuficienteException;
import java.math.BigDecimal;
import java.util.Objects;

public class C02_CuentaParaAnidadas
{
    private String persona;
    private BigDecimal saldo;
    
    private C02_BancoParaAnidadas banco;

    public String getPersona()
    {
        return persona;
    }

    public C02_CuentaParaAnidadas(String persona, BigDecimal saldo)
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

    public C02_BancoParaAnidadas getBanco()
    {
        return banco;
    }

    public void setBanco(C02_BancoParaAnidadas banco)
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
        final C02_CuentaParaAnidadas other = (C02_CuentaParaAnidadas) obj; //crea un objeto nuevo con lo mandado por parámetro
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
