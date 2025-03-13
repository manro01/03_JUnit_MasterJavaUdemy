/**
 * Lo que se prueba con las JUnit son un valor esperado contra el valor obtenido
 * Para ello jupiter (la parte de JUnit en la que se desarrolla el código) tiene
 * varios métodos Assertions que sirven para verificar resultados. 
 *
 * Los métodos de prueba deben ser independientes entre si,
 * NO DEBEN estar relacionados con otros métodos de la clase prueba.
 * 
 * Si en la clase prueba tenemos varios test y ejecutamos toda
 * la clase prueba (en lugar de un solo test), los test se van a 
 *  ejecutar en el orden que el motor de JUnit elija, no es por orden de aparición
 *  o por orden alfabético, es como JUnit decida.
 *      Mucha ATENCIÓN esto solo afecta a los test, si un test esta formado por varias
 *      pruebas (asserts) y una falla, finaliza la ejecución del test que lo 
 *      contenga. A menos que se agrupen las assertion (con Assertions.assertAll).
 *
 * La clase no tiene el modificador public, esto es porque las clases 
 * test por conveniencia no deben ser publicas, pues como son de pruebas
 * solo se usan con este fin, y al no tener modificardor se les pasa el modificador
 * DEFAULT que es el que da acceso al package
 * 
 * Para el caso de pruebas algunos desarrolladores optan por no utlizar
 * la regla cammel_case para el nombre de los test, sino que utilizan los
 * guiones para separar las palabras, esto es porque en el reporte que se genera
 * al final, aparecen los nombres de los test y se busca mejorar su descripición
 * es decir en lugar de testNombreCuenta se usa test_Nombre_Cuenta, pero en este
 * ejemplo se usa camel-case.
 *     Otra opción es usar la etiqueta @displayName pues permite tener un nombre
 * especifico para el reporte.
 *
 * En las pruebas unitarias SE DEBE evitar tener "estados" en las variables, es
 * decir, que no se deben tener test que dependan que una variable contenga cierto
 * valor, es decir que no se debería tener test que esperen que otro test modifique
 * una varible para que pueda continuar, (por ejemplo no se debería tener un test
 * que espere que antes se le haya cambiado el nombre al banco para realizar bien
 * su comprobación) esto es principalmente porque los test NO se realizan en un 
 * orden definido, pero también porque, el objetivo de los test unitarios es probar
 * pedazos de código independientes. Aunque NO se recomienda hacer si se puede hacer.
 * NOTA: Lo que si se puede hacer es tener una variable que se use en varios test
 * pero en cada test que se use se tiene que inicializar para que no dependa de un 
 * valor anterior,
 * NOTA: Se pude ordenar la ejecución de los test con la etiqueta @Order pero NO
 * SE RECOMIENDA en lo nás minímo.
 * 
 * Los test condicioneales sirven para tener ciertas condiciones que se esperan 
 * en un test, por ejemplo si es un sistema operativo especifico o si es una versión
 * de java especifica.
 * Recuera que las etiquetas de los condicionales, NO SON los test, son accesos
 *   a los test, es decir si se cumple la condición se entra al test o no (en caso
 *   que sea un disabled) pero ya adentro se hace un test, en caso de que adentro del
 *   no exista un assert, siempre va a dar positivo el test
 * 
 * Test Assumptions (de supocision) estos son como los condicionales normales
 *  pero en estos se SUPONE algo y sirven para evitar anular el test (mientras  que
 *  con los condicionales normales dan un error) esto es útil cuando el test no 
 *  se puede realizar porque falla algo, por ejemplo si el servidor esta activo
 *  se puede hacer el test, si no esta activo no tiene sentido que se haga.
 *  Los condicionales son etiquetas, y si no se sumple la condicíon, no entra
 *  en el test, los Assumptions son pruebas dentro del código del test, por lo que
 *  siempre entran y se van ejecutando hasta que se encuentra el assemption, si no 
 *  lo pasan se salta lo que resta del test, si lo cumple se termina la ejecución.
 * 
 *      Dentro de los Assumptions estan los assumptions.assumingThat(boolean prueba, Executable executable)
 *  donde prueba es un booleano, y executable es un objeto del tipo Executable por ejemplo una lambda
 *  Si la prueba es [ true ] se procede con lo que esta en el executable (por ejempolo lambda) y despés se
 *  continua con el resto del test, en caso de que la prueba sea [ false ], no se realiza lo del executable
 *  (por ejemplo una lambda) pero si se hace el resto del test
 *
 * Es la clase de prueba, en el caso del maestro, le dio las opciones para 
 *  generar varios métodos, en este caso, no se si no supe como hacerlo pero
 *  me genero muchos métodos por defautl, así que los borre todos para hacerlo manual
 *  como el maestro.
 *  NOTA: La diferencia es que el maestro usa intelliJ
 * 
 * 
 */
package ejemplos.models;

import ejemplos.exceptions.DineroInsuficienteException;
import java.math.BigDecimal;
import java.util.Properties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance; //es oara el @TestInstance(TestInstance.Lifecycle.PER_CLASS)
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.Assumptions;


//@TestInstance(TestInstance.Lifecycle.PER_CLASS) 
        //COn esto hacemos que solo exista una referencia de la clase, ESTO NO ES NADA RECOMENDABLE
        //  pero se puede hacer, con esto ya no se necesita que los test @AfeterALL y @BeforeAll 
        //  sean  static
class C01_CuentaTest
{
    String nombreCuenta= "Andres"; //esta variable la agregue yo, por lo tanto cuando se use
    // es porque yo la integre al ejemplo, creo que no se debe usar porque es un valor
    //  que se va a compartir entre varios test, pero como es el mismo igual y si es
    // valido, con valido me refiero a si se debe usar en los test
    
    //Este si lo puso el mestro y es correcto porque se inicializa para cada
    // prueba en la que se usa con el test @BeforeAll
    C01_Cuenta cuenta;
    
    /************************  TEST SIMPLES *************************************/
    
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
        

        //Cuenta cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
            //se va para el testAntesDeCada()
        
        
        //cuenta.setPersona("Andres");  //esto es para probar que se le pede asignar le valor a la variable persona
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Andres"));
    }
    
    @Test
    void testSaldoCuenta()
    {
        //cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));   //se va para el testAntesDeCada()
         
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
        cuenta= new C01_Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor real
        C01_Cuenta cuenta2= new C01_Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor esperado

        //Assertions.assertNotEquals(cuenta, cuenta2);
        Assertions.assertEquals(cuenta, cuenta2);
        //NORA MUY MUY IMPORTANTE:  assertEquals o assertNotEquals hacen uso de el método
        //  equals de las clases, que cuando no esta definido compara las referencias, 
        //  pero que podemos modificar para que compare lo que nosotros queremos 
        //  para saber si dos instancias son iguales, entonces si no se modifica el equals en el objeto
        //  va a comparar que las referencias sean iguales, pero si se modifica el equals
        //  va a comparar lo que se definio, en este caso si se modifico la clase C01_Cuenta
        //  para que comparara los valores de persona y saldo
    }
    
    @Test
    void testDebitoCuenta()
    {
        //cuenta = new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se fue a testAntesDeCada()
        cuenta.debito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
        Assertions.assertEquals(900, cuenta.getSaldo().intValue()); //si a 1000.12345 le restamos 100 y lo hacemos int debe dar 900
        Assertions.assertEquals("900.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
    }
    
    @Test
    void testCreditoCuenta()
    {
        //Cuenta cuenta = new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se fue a testAntesDeCada()
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
        //Cuenta cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se fue a testAntesDeCada()
        
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
        C01_Cuenta cuenta1 = new C01_Cuenta("Jhon Doe", new BigDecimal("2500"));
        C01_Cuenta cuenta2= new C01_Cuenta("Andres", new BigDecimal("1500.8989"));
        
        C01_Banco banco= new C01_Banco();
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
        C01_Cuenta cuenta1 = new C01_Cuenta("Jhon Doe", new BigDecimal("2500"));
        C01_Cuenta cuenta2= new C01_Cuenta("Andres", new BigDecimal("1500.8989"));
        
        C01_Banco banco= new C01_Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        
        banco.setNombre("Bital");
        
        //Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
        Assertions.assertEquals(2, banco.getCuentas().size()); //probamos que banco tenga 2 cuentas relacionadas
        
        //COn esto vamos a probar que una cuenta este relacionado a un banco
        Assertions.assertEquals("Bital", cuenta1.getBanco().getNombre());
        //Tal como estaba el código esta prueba se fallaba, porque cuando se agrgaba una
        //  cuenta al banco NO se agregaba el banco a la cuenta, por lo tanto teniamos
        //  cuentas sin banco, pero Si teniamos C01_Banco asociado con cuentas, 
        //  C01_Banco asociado cuenta1          cuenta1 banco asociado null
        //                 cuenta2          cuenta2 banco asociado null
        //Para arreglarlo se tuvo que agregar en C01_Banco.addCuenta(C01_Cuenta cuenta)
        //  una linea para agregar el banco a la cuenta, quedando así
        //
        //public void addCuenta(C01_Cuenta cuenta)
        //{
        //      cuentas.add(cuenta);
        //      cuenta.setBanco(this); //Con esto a cada cuenta le asociamos este banco
        //}
        //Con esto ya funciona bien
        //
        //Este es precisamente un buen ejemplo de las pruebas unitarias, como 
        //  se aprecia, parecia que esta bien el programa pues un C01_Banco tenia bien
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
    
    
/************************  TEST AGRUPADOS *************************************/
    
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
        C01_Cuenta cuenta1 = new C01_Cuenta("Jhon Doe", new BigDecimal("2500"));
        C01_Cuenta cuenta2= new C01_Cuenta("Andres", new BigDecimal("1500.8989"));
        
        C01_Banco banco= new C01_Banco();
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
    
    
/************************  TEST CON MENSAJES *************************************/
    /**
     * Se va a probar el mandar un mensaje de error en los asserts.
     *  Para mostrar un mensaje de error personalizado, el mensaje se manda como 
     *  parémetro del assert
     * Este ejemplo viene de méodo testNombreCuenta de esta misma clase.
     * 
     * NOTA MUY MUY IMPORTANTE: EL mensaje de error, esta diseñado para que solo
     *  se cargue cuando se presente el error, por lo tanto se lo declaramos
     *  como un String directamente, estaríamos haciendo algo no recomendable, 
     *  pues los String se cargan en memorio, lo mejor es, declarar alguna función
     *  que entre cuando se produce el error.
     */
    @Test 
    void testNombreCuentaConMensaje()
    {
        
        C01_Cuenta cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  
        
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        Assertions.assertEquals(esperado, real, "El nombre de la cuenta no es el que se espera"); //manera no recomendada
        Assertions.assertTrue(real.equals("Andres"), () -> "Nombre de cuenta esperada debe ser igual a la real"); //forma correcta con lambdas
    }
    
    /**
     * Seguimos probando los test con mensaje, en este caso vamos a mejorar
     *  el test testAssertionsAgrupadas().
     * Como vimos el test original (testAssertionsAgrupadas()) tenia un gran problema
     *  pues como realizaba varias pruebas de true y otros de equals era difícil
     *  encontrar el problema pues el mensaje por default solo señalaba que se encontro
     *  un false en lugar de un true, pero no decía donde, ahora que ya se vio el 
     *  concepto de mandar mensajes ya se puede especificar mejor donde viene el error.
     * 
     * NOTA: En este test, se puede tener varios errores pues se depende mucho
     *  que los valores que dan error y los que estan en el mensaje son idependientes
     *  pero representan lo mismo, por ejemplo el nombre del banco se pone, como
     *  valor esperado y luego se pone como parte del menaje, esto sería mejor
     *  si se usaran variable, pero no se si se pueda en el tema de test, poruqe
     *  en los métodos si se puede y además así estaba el ejemplo.
     * 
     * NOTA MUY MUY IMPORTANTE: como apenas estoy comprendiendo las lambdas las puse de 
     *  una manera que me fuera facíl entenderlas, pero no se pondrían así ya cuendo
     *  se trabja, con esto me refiero a la identación, también esta el temas de
     *  poner o no {} pero esto no es ningúna regla
     * 
     */
    @Test
    void testAssertionsAgrupadasConMensaje()
    {
        C01_Cuenta cuenta1 = new C01_Cuenta("Jhon Doe", new BigDecimal("2500"));
        C01_Cuenta cuenta2= new C01_Cuenta("Andres", new BigDecimal("1500.8989"));
        
        C01_Banco banco= new C01_Banco();
        banco.setNombre("Bital");
        
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        
        Assertions.assertAll( 
                () -> {//Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
                        Assertions.assertEquals(
                                            2, banco.getCuentas().size(), 
                                                    ()->{
                                                        //esta segunda lambda es para el mensaje
                                                            return "Se esperaban 2 cuentas relacionadas al banco "
                                                                + banco.getNombre() + " y solo se encontraon " 
                                                                + banco.getCuentas().size();
                                                        }
                                                );   
                      }, 
                () -> {//Con esto vamos a probar que una cuenta este relacionado a un banco
                        Assertions.assertEquals(
                                            "Bital", cuenta1.getBanco().getNombre(), 
                                                   ()->{ 
                                                       //esta segunda lambda es para el mensaje
                                                        return "La cuenta1 de " + cuenta1.getPersona() 
                                                                + " NO esta relacionda al banco Bital, sino "
                                                                + "al banco " + cuenta1.getBanco().getNombre();
                                                       }
                                                );
                      }, 
                () -> {//Con esto vamos a probar si dentro de las cuentas del banco tenemos la cuenta de [Andres]
                        //NOTA si esto lo que revisa es que la cuenta que se espera y la que se obtuvo sean iguales
                        // pero si si el stream no regresa ninguna cuenta (porque no encontro coincidencias) manda una
                        //  excepciónque no esta cubierta por el mensaje, por lo tanto no sale el mensaje personalizado
                        //  sino el de la excepción
                        Assertions.assertEquals(
                                            "Andres", banco.getCuentas() //en esta linea hacemos el test
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres")) //Esta es la linea que puede dar excepción
                                                            .findFirst()
                                                            .get()
                                                            .getPersona(), 
                                                    () -> {
                                                            //esta segunda lamba es para el mensaje
                                                            return "La cuenta de Andres no se encuentra en el banco ";
                                                          }
                                                );
                      },
                () -> {//prueba casi lo mismo que el anterior pero ahora prueba si la cuenta de [Andres] esta presente
                        //Este método si revisa si la cuenta existe dentro del banco
                        Assertions.assertTrue(
                                            banco.getCuentas()
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres"))
                                                            .findFirst()
                                                            .isPresent(),
                                                    ()->{ 
                                                            //esta segunda lambda es para el mensaje
                                                            return "La cuenta Andres no se encuntra en el banco " 
                                                                    + banco.getNombre();
                                                        }
                                             );
                      },
                () -> //prueba lo mismo que el anterior pero de otra forma
                        Assertions.assertTrue(
                                            banco.getCuentas()
                                                            .stream()
                                                            .anyMatch(c -> c.getPersona()
                                                            .equals("Andres")),
                                                    ()-> "No existe esta cuenta en el banco " + banco.getNombre()
                                                        //Esta segunda lamba es para el mensaje
                                            )
                      //Recuerda que si una lambda, sólo tiene un método y que es lo que va a regresas, NO es
                      //    necesario poner las {}, estas son las mismas lambda que las de arriba pero ya aplicando
                      //    esta regla, esto mejora la presentación, pero HACE lo mismo, se pusieron
                      //    las otras formar para que se entendiera mejor, pero es los mismo
                );
    }
    
/************************  TEST ANOTACIONES *************************************/
    
    /**
     * Las anotaciones son para usar algunas funciones, ya incluidas dentro de
     *  jupiter.
     * Vamos a usar de base del test testNombreCuenta().
     * NOTA MUY MUY MUY IMPORTANTE: NO FUNCIONA EL DISPLAYNAME, puede ser la versión de
     * java, o el IDE pero no me funciona
     */
    @Test
    @DisplayName("Probando testAnotaciones")
    @Disabled //deshabilita la prueba, es como si se la saltara
    void testAnotaciones()
    {
        Assertions.fail(); ///****** fail fuerza que falle el test, no importa si la prueba esta bien, se fuerza su fallo.
                            //**** fail no es una etiqueta, esun método
        C01_Cuenta cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
        
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Andres"));
    }
    
    /**
     * Queremos que este test se ejecute anted de cualquier otro, y por eso
     *  le dejamos la creación de un objeto cuenta que va a ser común a varias 
     *  pruebas, pero no en todas, en esos caso SI se inicializa cuenta en sus test
     */
    @Test
    @BeforeEach //Esto se ejecuta antes de cada método
    void testAntesDeCada()
    {
        System.out.println("Iniciando prueba");
        this.cuenta= new C01_Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
    }
    
    @Test
    @AfterEach
    void testDespuesDeCada()
    {
        System.out.println("Finalizando prueba");
        System.out.println("");
    }
    
    /**
     * El test que use la etiqueta @BeforeAll tiene que SER STATIC, esto es porque
     *  se va a usar antes que cualquier otro test y esto implica que pudiera acceder
     *  a recursos que se crean en otro test, si lo hacemos static nos aseguramos
     *  que se ejecute primero y podemos iniciar los recursos requeridos.
     */
    @Test
    @BeforeAll
    static void antesQueTodo()
    {
        System.out.println("Inicializando todas las ruebas");
    }
    
    /**
     * El test que use la etiqueta @AfterAll tiene que SER STATIC, esto es porque
     * siempre se va a ejecutar, despues de los otros test, y esto implica que pudiera acceder
     *  a recursos que se crean en otro test, si lo hacemos static nos aseguramos
     *  que se ejecute con los recutsos que necesita.
     */
    @Test
    @AfterAll
    static void despuesDeTodo()
    {
        System.out.println("Finalizando todas las pruebas");
    }
    
/************************  TEST CONDICIONALES *************************************/
//Recuera que las etiquetas de los condicionales, NO SON los test, son accesos
//   a los test, es decir si se cumple la condición se entra al test o no (en caso
//   que sea un disabled) pero ya adentro se hace un test, en caso de que adentro del
//   no exista un assert, siempre va a dar positivo el test
// En este caso en todos los test condicionales se puso el assert para comparar1
//  que nombreCuenta sea igual al nombre asociado a la cuenta [ cuenta.getPersona() ]
//  estas variable y los asserts los puse yo para ejemplificar, no se si el uso
//  de la variable nombreCuenta este correcto para los test unitarios
    
    /**
     * Esta prueba solo se habilita si se hace en un S.O. windows
     */
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows()
    {
        System.out.println("Esta prueba se realiza se activa si el S.O. es windows");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * Esta prueba solo se habilita si se hace en un S.O. Linux
     */
    @Test
    @EnabledOnOs(OS.LINUX)
    void testSololinux()
    {
        System.out.println("Esta prueba se realiza se activa si el S.O. es Linux");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * Esta prueba se deshabilita si se hace en un S.O. Windows o un S.O. Mac
     * Para agrupar varias condicionales se hace como si fuera un arreglo
     * {valor1, valor2, valor3, etc}
     */
    @Test
    @DisabledOnOs({OS.WINDOWS, OS.MAC})
    void testNoWindowsNoMac()
    {
        System.out.println("Esta prueba se desactiva si el S.O. es windows o mac");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * Esta prueba se habilita si se hace en en JVE 8
     */
    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testOnJava8()
    {
        System.out.println("Esta prueba se activa se activa si la JRE es la 8");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * Esta prueba se habilita si se hace en en JVE 11
     */
    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void testOnJava11()
    {
        System.out.println("Esta prueba se activa se activa si la JRE es la 11");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * Esta prueba se habilita si la valor de la propiedad user.lenguege
     * coincide con es
     */
    @Test
    @EnabledIfSystemProperty(named = "user.language", matches = "es")
    void testOnPropertyUserLanguage()
    {
        System.out.println("Esta prueba se activa si la property user.language = es");
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
    }
    
    /**
     * No es un test como tal, solo es para mostrar las propertyes que tiene java
     */
    @BeforeAll
    @Test
    static void showProperties()
    {
        Properties properties= System.getProperties();
        properties.forEach((k, v)-> System.out.println(k + ":" + v));
    }
    
/************************  TEST ASSUMPTION (SUPOSICIONES)*************************************/
//Los Assumptions son parecidos a los condicionales normales, la diferencia es que los
//  assumptions NO SON etiquetas son comprobaciónes que se dan dentro del código del test
//  que otra diferencia es que con las condicionales, no entraba al test con los Assumptions
//  si entra al test e incluso se puede hacer cosas, hasta que se haga el Assumption
//  es entonces que si no se cumple de salta el test, si se cumple continua el test.
    
    /**
     * Si la propiedad del idioma es "en" entonces se termina el test, si no es "en
     *  se salta el test
     */
    @Test
    void testLanguageEs()
    {
        System.out.println("(assumption)Esta prueba se realizara solo si la property user.language = en");
        
        boolean verificacionIdioma= "en".equals(System.getProperty("user.language"));

        Assumptions.assumeTrue(verificacionIdioma);
        
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        
        
    }
    
    /**
     * Con las Assumption tenemos la prueba Assumptions.assumingThat(prueba, excutable) 
     * donde prueba es lo que se va a comparar y executable es un método que sea del 
     * tipo Executable como las lambdas, ver el ejemplo para más información 
     * Este test permite que en caso de NO CUMPLIR la prueba lo demás del test 
     * continua su ejecución saltandose lo que esta en el executable, en caso 
     * de SI CUMPLIR con la condición se procede con el executbale, donde podemos
     *  tener otras pruebas
     */
    @Test
    void testAssumptionAssumingThat()
    {
        cuenta.setBanco(new C01_Banco());
        cuenta.getBanco().setNombre("Bital");
        //establecimos el nombre del banco, esto es para la pruenba en caso de que se cumpla
        //el lenguaje
        
        System.out.println("(assumingThat)Esta prueba se hara completa solo si la property user.language = en");
        
        boolean verificacionIdioma= "en".equals(System.getProperty("user.language"));

        // Si el lennguage SI ES "en" se procede con lo que esta dentro
        //  de la lambda y después se continua con el resto de este test. Pero
        //  si el lenguage NO ES "en", entonces no se entra en la lambda y se sigue 
        //  con el resto de este test, pero si el lenguage 
        Assumptions.assumingThat(verificacionIdioma, ()->{
            System.out.println("El lenguage es \"en\", por lo que se entro en la lambda");
            Assertions.assertEquals("Bitall", cuenta.getBanco().getNombre());
            //NOTA: esta prueba se va a fallar, porque el nombre del banco es Bital y 
            //  y que se esta eperando aquí es Bittal por lo que lo esperado y lo real no
            // coincide, esta hecho así para demostrar el error
        });
        
        Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        
    }
    
}



