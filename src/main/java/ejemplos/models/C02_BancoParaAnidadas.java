/*
 * Es lo mismo que Banco, solo que esta es para C02_CuentaParaAnidadas
 * se tuvo que crear esta porque Banco esta relacionado con Cuenta, 
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C02_BancoParaAnidadas
{
    private String nombre;
    private List<C02_CuentaParaAnidadas> cuentas;
    
    public C02_BancoParaAnidadas()
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

    public List<C02_CuentaParaAnidadas> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C02_CuentaParaAnidadas> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C02_CuentaParaAnidadas cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C02_CuentaParaAnidadas origen, C02_CuentaParaAnidadas destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
