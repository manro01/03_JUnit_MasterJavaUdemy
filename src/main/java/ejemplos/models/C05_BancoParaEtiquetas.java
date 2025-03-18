/*
 * Es lo mismo que C04_BancoParaParametizer, pero para usar con C05_CuentaParaEtiquetasTest
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C05_BancoParaEtiquetas
{
    private String nombre;
    private List<C05_CuentaParaEtiquetas> cuentas;
    
    public C05_BancoParaEtiquetas()
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

    public List<C05_CuentaParaEtiquetas> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C05_CuentaParaEtiquetas> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C05_CuentaParaEtiquetas cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C05_CuentaParaEtiquetas origen, C05_CuentaParaEtiquetas destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
