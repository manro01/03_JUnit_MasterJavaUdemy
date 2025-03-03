/*
 * Lo que se prueba con las JUnit son un valor esperado contra el valor obtenido.
 * Para ello jupiter(la parte de JUnit en la que se desarrolla el código) tiene
 * varios métodos Assertions que sirven para verificar resultados.
 *
 * NOTA MUY MUY IMPORTANTE: Los métodos de prueba deben ser independientes entre si,
 * NO DEBEN estar relacionados con otros métodos de la clase prueba.
 * 
 * NOTA: Si en la clase prueba tenemos varios métodos de prueba y ejecutamos toda
 * la clase prueba (en lugar de un solo método), los métodos de prueba se van a 
 *  ejecutar en el orden que el motor de JUnit elija, no es por orden de aparición
 *  o por orden alfabético, es como Junit decida.
 * 
 * Es la clase de prueba, en el caso del maestro, le dio las opciones para 
 *  generar varios métodos, en este caso, no se si no supe como hacerlo pero
 *  me genero muchos métodos por defautl, así que los borre todos para hacerlo manual
 *  como el maestro.
 *  NOTA: La diferencia es que el maestro usa intelliJ
 * 
 * NOTA: La clase no tiene el modificador public, esto es porque las clases 
 * de pruebas por conveniencia no deben ser publicas, pues como son de pruebas
 * solo se usan con este fin, y al no tener modificardor se les pasa el modificador
 * DEFAULT que es el que da acceso al package
 * 
 * NOTA: Para el caso de pruebas algunos desarrolladores optan por no utlizar
 * la regla cammel_case para el nombre de los métodos, sino que utilizan los
 * guiones para separar las palabras, esto es porque en el reporte que se genera
 * al final, aparecen los nombres de los métodos y se busca mejorar su descripición
 * es decir en lugar de testNombreCuenta se usa test_Nombre_Cuenta, por en este
 * ejemplo se usa camel-case.
 *     Otra opción es usar la etiqueta @displayName pues permite tener un nombre
 * especifico para el reporte, por ahora no se va a usar esta opción.
 * 
 * NOTA MUY IMPORTANTE: Recuerda que las prubeas son comparar un valor deseado
 *  contra el valor que se obtiene;
 * 
 * 
 */
package ejemplos.models;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CuentaTest
{
    @Test ///Es para indicar que va a ser un métopo para las pruebas
    void testNombreCuenta()
    {
        //Es teste caso lo que vamos a verificar es que la persona esperada sea Andres, para
        //  esto se puede hacer de dos formas, usando el setPersona para asignar una persona
        //  o usando un constructor que reciba por lo menos persona
        //  Aunque las dos hacaen la prueba, se usan para cosas diferentes, una prueba
        //  que funcione el setPersona y otra prueba que el constructor este bien.
        // Dependiendo del programa y lo que se hace se puede tener uno, dos o más
        //  pruebas de este tipo.
        Cuenta cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
        
        //cuenta.setPersona("Andres");  //esto es para probar que se le pede asignar le valor a la variable persona
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Andres"));
    }
    
    @Test
    void testSaldoCuenta()
    {
        Cuenta cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));
        
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        //lo que se va a hacer es probar que el valor de saldo sea iguarl a 1000.12345 pero en valor double
        //  es por queso que cuando se recupero el valor con getSaldo se convirtio a Double
        //  esto es solo para probar no tiene nada que ver con las capacidades para probar los
        //  valores de JUnit
        
        //COMPARAR si valor de saldo de cuenta sea negativo
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) <0);
        //BigDecimal no puede directamente hacer un numero > 2, ( es decir no puede
        //  comparar que un bigdecimal sea mayor a otro numero, se tiene que hacer
        //  de otra manera en este caso 
        //  compareTo devuelve -1, 0 o 1, -1 si el bigDecimal que esta comparando
        //      es menor al que se va a comparar.
        //   0 si el bigDecimal que se esta comparando es igual al que se va a 
        //      comparar.
        //   1 si el bigdecimal que se esta comparando es mayor al que se va 
        //      a comparar.
        //  Entonces primero hacemos la comparacion del bigdecimal obtenido del 
        //      cuenta contra un bigDecimal.Zero de esta forma sabemos si es negativo
        //  cero o positivo. 
        //  Y como queremos saber si el bigDecimal de cuenta es negativo se compara
        //  con un menor que cero
    }
    
    //se va a revisar si las referencias a un objeto son iguales, por defecto no deberían ser iguales
    @Test
    void testIgualdadCuenta()
    {
        Cuenta cuenta= new Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor real
        Cuenta cuenta2= new Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor esperado
        
        System.out.println(cuenta);
        System.out.println(cuenta2);
        
        //Assertions.assertNotEquals(cuenta, cuenta2);
        Assertions.assertEquals(cuenta, cuenta2);
        //NORA MUY MUY IMPORTANTE:  assertEquals o assertNotEquals hacen uso de el método
        //  equals de las clases, que cuando no esta definido compara las referencias, 
        //  pero que podemos modificar para que compare lo que nosotros queremos 
        //  para saber si dos instancias son iguales, entonces si no se modifica el equals en el objeto
        //  va a comparar que las referencias sean iguales, pero si se modifica el equals
        //  va a comparar lo que se definio, en este caso si se modifico la clase Cuenta
        //  para que comparara los valores de persona y saldo
    }
    
    @Test
    void testDebitoCuenta()
    {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
        Assertions.assertEquals(900, cuenta.getSaldo().intValue()); //si a 1000.12345 le restamos 100 y lo hacemos int debe dar 900
        Assertions.assertEquals("900.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
    }
    
    @Test
    void testCreditoCuenta()
    {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
        Assertions.assertEquals(1100, cuenta.getSaldo().intValue()); //si a 1000.12345 le sumamos 100 y lo hacemos int debe dar 1100
        Assertions.assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
    }
    
    
}
