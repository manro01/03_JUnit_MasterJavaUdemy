/*
 * Es lo mismo que C05_BancoParaInyeccionDependencias, pero para usar con C06_CuentaParaInyeccionDependendenciasTest
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C06_BancoParaInyeccionDependencias
{
    private String nombre;
    private List<C06_CuentaParaInyeccionDependencias> cuentas;
    
    public C06_BancoParaInyeccionDependencias()
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

    public List<C06_CuentaParaInyeccionDependencias> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C06_CuentaParaInyeccionDependencias> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C06_CuentaParaInyeccionDependencias cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C06_CuentaParaInyeccionDependencias origen, C06_CuentaParaInyeccionDependencias destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
