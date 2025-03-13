/*
 * Es lo mismo que C03_BancoParaRepetidas, pero para usar con C04_CuentasParaParametizerTest
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C04_BancoParaParametizer
{
    private String nombre;
    private List<C04_CuentaParaParametizer> cuentas;
    
    public C04_BancoParaParametizer()
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

    public List<C04_CuentaParaParametizer> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C04_CuentaParaParametizer> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C04_CuentaParaParametizer cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C04_CuentaParaParametizer origen, C04_CuentaParaParametizer destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
