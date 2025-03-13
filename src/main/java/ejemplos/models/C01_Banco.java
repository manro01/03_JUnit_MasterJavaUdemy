/*
 * Clase que simula unbanco
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C01_Banco
{
    private String nombre;
    private List<C01_Cuenta> cuentas;
    
    public C01_Banco()
    {
        cuentas= new ArrayList<>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List<C01_Cuenta> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C01_Cuenta> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C01_Cuenta cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C01_Cuenta origen, C01_Cuenta destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
