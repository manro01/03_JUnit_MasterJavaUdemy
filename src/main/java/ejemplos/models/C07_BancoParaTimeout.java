/*
 * Es lo mismo que C06_BancoParaInyeccionDependencias, pero para usar con C07_CuentaParaTimeout
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C07_BancoParaTimeout
{
    private String nombre;
    private List<C07_CuentaParaTimeout> cuentas;
    
    public C07_BancoParaTimeout()
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

    public List<C07_CuentaParaTimeout> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C07_CuentaParaTimeout> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C07_CuentaParaTimeout cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C07_CuentaParaTimeout origen, C07_CuentaParaTimeout destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
