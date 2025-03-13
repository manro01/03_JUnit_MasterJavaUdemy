/*
 * Es lo mismo qu banco, solo que esta esta es el Banco para CuentasParaAnidadas
 * se tuvo que crear esta porque Banco esta relacionado con Cuenta y como en 
 * CuentaParaAnidadasTest, hacemos una prueba con el banco, tenemos que crear 
 * un banco para anidadas
 * Viene de C02_BancoParaAnidadas
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class C03_BancoParaRepetidas
{
    private String nombre;
    private List<C03_CuentaParaRepetidas> cuentas;
    
    public C03_BancoParaRepetidas()
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

    public List<C03_CuentaParaRepetidas> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<C03_CuentaParaRepetidas> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(C03_CuentaParaRepetidas cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(C03_CuentaParaRepetidas origen, C03_CuentaParaRepetidas destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
