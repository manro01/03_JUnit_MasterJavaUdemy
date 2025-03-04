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
 *      Mucha ATENCIÓN esto solo afecta a los métodos, si un método tiene varias
        pruebas (asserts) y una falla, finaliza la ejecución del método que lo 
        contenga. A menos que se agrupen las assertion (con Assertions.assertAll).
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

import ejemplos.exceptions.DineroInsuficienteException;
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
    
    /**
     * En este test se van a probar dos cosas.
     * Primero es que salte una excepción cuando se quiere usar el método debito 
     *  porque no hay fondos suficientes. (SI salta la excepción, esta
     *  prueba esta CORRECTA, si NO salta la excepció esta prueba esta MAL) e incluso que 
     *  la excepción sea del mismo tipo, aquí definimos que la exepción es del 
     *  tipo DineroInsuficienteException.class, si recibimos una de otro tipo por 
     *  ejemplo NullPointerexcepction tambien falla este test
     * Segundo es que el mensaje de excepción que sale se igual al que esperamos
     *  que salga (que definimos aquí) 
     */
    @Test
    void testDineroInsuficienteException()
    {
        //NOTA: recuera que tal como esta el ejemplo, para que pase este test tiene que saltar
        //  la excepción de que la cuenta no tiene el saldo suficiente para hacer la transacción
        //  Si la transacción se hace con exito, este test falla.
        Cuenta cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));
        
        Exception exception= Assertions.assertThrows(DineroInsuficienteException.class, ()->{
            cuenta.debito(new BigDecimal(15000));
            //Se guarda el tipo de error que regresa assertionThrows
        });
        
        String actual= exception.getMessage();
        String esperado= "Dinero Insuficiente";
        Assertions.assertEquals(esperado, actual);
    }
    
    @Test
    void testTransferirDineroCuentas()
    {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2= new Cuenta("Andres", new BigDecimal("1500.8989"));
        
        Banco banco= new Banco();
        banco.setNombre("Bital");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        
        Assertions.assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        Assertions.assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }
    
    /**
     * Aquí se van a probar tres cosas
     * 1. Que el banco tenga cuentas relacionadas
     * 2. Que las cuentas tengan el banco relacionado
     * 3. Que la relación de una persona(Cuenta.persona) este
     *      ecista en el banco, es decir no solo que el banco
     *      este relacionado sino que los datos de una cuenta 
     *      esten bien relacionados en un banco.
     */
    @Test
    void testRelacionBancoCuentas()
    {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2= new Cuenta("Andres", new BigDecimal("1500.8989"));
        
        Banco banco= new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        
        banco.setNombre("Bital");
        
        //Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
        Assertions.assertEquals(2, banco.getCuentas().size()); //probamos que banco tenga 2 cuentas relacionadas
        
        //COn esto vamos a probar que una cuenta este relacionado a un banco
        Assertions.assertEquals("Bital", cuenta1.getBanco().getNombre());
        //Tal como estaba el código esta prueba se fallaba, porque cuando se agrgaba una
        //  cuenta al banco NO se agregaba el banco a la cuenta, por lo tanto teniamos
        //  cuentas sin banco, pero Si teniamos Banco asociado con cuentas, 
        //  Banco asociado cuenta1          cuenta1 banco asociado null
        //                 cuenta2          cuenta2 banco asociado null
        //Para arreglarlo se tuvo que agregar en Banco.addCuenta(Cuenta cuenta)
        //  una linea para agregar el banco a la cuenta, quedando así
        //
        //public void addCuenta(Cuenta cuenta)
        //{
        //      cuentas.add(cuenta);
        //      cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
        //}
        //Con esto ya funciona bien
        //
        //Este es precisamente un buen ejemplo de las pruebas unitarias, como 
        //  se aprecia, parecia que esta bien el programa pues un Banco tenia bien
        //  sus referencias a cuentas, pero cuando se provo al reves (que una cuenta
        //  estuviera asociada a un banco) fue que dio error, ya con eso trabjamos
        //  en contrarrestar el error.
        
        
        //Con esto vamos a probar si dentro de las cuentas del banco tenemos la cuenta de [Andres]
        Assertions.assertEquals("Andres", banco.getCuentas()
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres"))
                                                            .findFirst()
                                                            .get()
                                                            .getPersona());
        
        //prueba casi lo mismo que el anterior pero ahora prueba si la cuenta de [Andres] esta presente
//        Assertions.assertTrue(banco.getCuentas()
//                                   .stream()
//                                   .filter(c -> c.getPersona()
//                                   .equals("Andres"))
//                                   .findFirst()
//                                   .isPresent());
        
        //prueba lo mismo que el anterior pero de otra forma
        Assertions.assertTrue(banco.getCuentas()
                                   .stream()
                                   .anyMatch(c -> c.getPersona()
                                   .equals("Andres")));
    }
    
    /**
     * Aquí se van a probar las assertion agrupadas, Recuera que si no estan agrupadas
     *  assertions que estan dentro de un método y una falla, se termina la pruba
     *  y no se especifica si las otras fallaron, para eso hay que agruparlas.
     * NOTA. Este ejemplo esta basado en el método testRelacionBancoCuentas(),
     * porlo que aquí se borraron los comentarios, si hay dudas revisar el 
     * testRelacionBancoCuentas().
     * 
     * El objetivo es tener varios assertions agrupados para ver que pasa si uno
     *  de ellos falla
     * 
     * NOTA MUY MUY IMPORTANTE: El objetivo se cumplio, si se ven los errores
     *  pero en este caso pusimos 2 errores de true false, cuando cualquiera
     *  de ellos falla solo dice que se esperaba un valor true pero se encontro un false
     *  por lo que va  aser dificíl encontrar el errror, pero con los otros
     *  assert (son equals) si funciona bien pues muestra que el error esta en el 
     *  valor obtenido y el valor que se espera, por lo que es facíl encontrar el 
     *  error.
     */
    @Test
    void testAssertionsAgrupadas()
    {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2= new Cuenta("Andres", new BigDecimal("1500.8989"));
        
        Banco banco= new Banco();
        banco.setNombre("Bital");
        
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        
        Assertions.assertAll( 
                () -> {//Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
                        Assertions.assertEquals(2, banco.getCuentas().size()); }, 
                () -> {//Con esto vamos a probar que una cuenta este relacionado a un banco
                        Assertions.assertEquals("Bital", cuenta1.getBanco().getNombre());}, 
                () -> {//Con esto vamos a probar si dentro de las cuentas del banco tenemos la cuenta de [Andres]
                        Assertions.assertEquals("Andres", banco.getCuentas()
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres"))
                                                            .findFirst()
                                                            .get()
                                                            .getPersona());},
                () -> {//prueba casi lo mismo que el anterior pero ahora prueba si la cuenta de [Andres] esta presente
                        Assertions.assertTrue(banco.getCuentas()
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres"))
                                                            .findFirst()
                                                            .isPresent());},
                () -> {//prueba lo mismo que el anterior pero de otra forma
                        Assertions.assertTrue(banco.getCuentas()
                                                            .stream()
                                                            .anyMatch(c -> c.getPersona()
                                                            .equals("Andres")));}
                );

        
    }
}
