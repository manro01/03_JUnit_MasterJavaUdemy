/*
 * Es lo mismo qu banco, solo que esta esta es el Banco para CuentasParaAnidadas
 * se tuvo que crear esta porque Banco esta relacionado con Cuenta y como en 
 * CuentaParaAnidadasTest, hacemos una prueba con el banco, tenemos que crear 
 * un banco para anidadas
 */
package ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BancoParaAnidadas
{
    private String nombre;
    private List<CuentaParaAnidadas> cuentas;
    
    public BancoParaAnidadas()
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

    public List<CuentaParaAnidadas> getCuentas()
    {
        return cuentas;
    }

    public void setCuentas(List<CuentaParaAnidadas> cuentas)
    {
        this.cuentas = cuentas;
    }
    
    public void addCuenta(CuentaParaAnidadas cuenta)
    {
        cuentas.add(cuenta);
        cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
    }
    
    public void transferir(CuentaParaAnidadas origen, CuentaParaAnidadas destino, BigDecimal monto)
    {
        origen.debito(monto); //saca el dinero de la cuenta origen
        destino.credito(monto); //guarda el dinero en la cuenta destino
    }
}
