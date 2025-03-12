/**
 * Esta clase de prueba es para probar las clases de prueba anidadas, hice esto 
 *  para no seguir modificando el CuentaTest, y tener lo que se vio ahi de respaldo.
 * 
 * Al igual que una clase normal las clases de test pueden tener clases anidadas, 
 * con el uso de estas podemos tener un orden sobre como se ejecutan las pruebas
 * además de que mejora la visibilidad.
 * 
 * Con la etiqueta @Nested podemos definie que las innerClass van a ser tratadas 
 * como si fueran metodos de test normales y por lo tanto responden a las etiquetas
 * @BeforeEach, @AfterEach, @ExtendWith 
 *      Sin embargo con @BeforaAll y @AfterAll hay una cuestion y es que unicamente
 *  las clases NO ESTATICAS pueden ser @Nested, pues como vimos en el ejemplo
 *  CuentaTest los métodos que declarabamos como @BeforeAll y @AfterAll se tenian
 *  que poner como ESTATICOS, aunque también vimos que una forma de darle la vuelta
 *  a esto era usar la etiqueta @TestInstance(Lifecycle.PER_CLASS) por lo que la tendremos
 *  que usar si queremos usar @BeforeAll y @AfterAll.
 * Otra solución es que los @BeforeAll y @AfterAll esten únicamente en la clase
 *  principal NO en las innerclass.
 * 
 * Casi todos los test que se hicieron aquí son repetidos de CuentaTest.java por
 * lo tanto si hay duads ve para allá
 */
package ejemplos.models;

import ejemplos.exceptions.DineroInsuficienteException;
import java.math.BigDecimal;
import java.util.Properties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


public class CuentaParaAnidadasTest
{
    String nombreCuenta= "Andres"; //esta variable la agregue yo, por lo tanto cuando se use
    // es porque yo la integre al ejemplo, creo que no se debe usar porque es un valor
    //  que se va a compartir entre varios test, pero como es el mismo igual y si es
    // valido, con valido me refiero a si se debe usar en los test
    
    //Este si lo puso el mestro y es correcto porque se inicializa para cada
    // prueba en la que se usa con el test @BeforeAll
    CuentaParaAnidadas cuenta;
    
    @Test
    @DisplayName("Probando testAnotaciones")
    @Disabled //deshabilita la prueba, es como si se la saltara
    void testAnotaciones()
    {
        fail(); ///****** fail fuerza que falle el test, no importa si la prueba esta bien, se fuerza su fallo.
                            //**** fail no es una etiqueta, esun método
        //CuentaParaAnidadas cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
        
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        assertEquals(esperado, real);
        assertTrue(real.equals("Andres"));
    }
    
    @Test
    @BeforeEach //Esto se ejecuta antes de cada método
    void testAntesDeCada()
    {
        System.out.println("Iniciando prueba");
        this.cuenta= new CuentaParaAnidadas("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
    }
    
    @Test
    @AfterEach
    void testDespuesDeCada()
    {
        System.out.println("Finalizando prueba");
        System.out.println("");
    }
    
    @Test
    @BeforeAll
    static void antesQueTodo()
    {
        System.out.println("Inicializando todas las ruebas");
    }
    
    @Test
    @AfterAll
    static void despuesDeTodo()
    {
        System.out.println("Finalizando todas las pruebas");
    }
    
    /************************  TEST SIMPLES *************************************/
    
    @Nested
    class SimplesTest
    {

        @Test ///Es para indicar que va a ser un métopo para las pruebas
        void testNombreCuenta()
        {
            String esperado = "Andres";
            String real = cuenta.getPersona();

            assertEquals(esperado, real);
            assertTrue(real.equals("Andres"));
        }

        @Test
        void testSaldoCuenta()
        {

            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        }

        @Test
        void testIgualdadCuenta()
        {
            cuenta = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("8900.997")); //valor real
            CuentaParaAnidadas cuenta2 = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("8900.997")); //valor esperado

            assertEquals(cuenta, cuenta2);
        }

        @Test
        void testDebitoCuenta()
        {
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
            assertEquals(900, cuenta.getSaldo().intValue()); //si a 1000.12345 le restamos 100 y lo hacemos int debe dar 900
            assertEquals("900.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
        }

        @Test
        void testCreditoCuenta()
        {
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
            assertEquals(1100, cuenta.getSaldo().intValue()); //si a 1000.12345 le sumamos 100 y lo hacemos int debe dar 1100
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
        }

        @Test
        void testDineroInsuficienteException()
        {

            Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
                cuenta.debito(new BigDecimal(15000));
            });

            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";
            assertEquals(esperado, actual);
        }

        @Test
        void testTransferirDineroCuentas()
        {
            CuentaParaAnidadas cuenta1 = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("2500"));
            CuentaParaAnidadas cuenta2 = new CuentaParaAnidadas("Andres", new BigDecimal("1500.8989"));

            BancoParaAnidadas banco = new BancoParaAnidadas();
            banco.setNombre("Bital");
            banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }

        @Test
        void testRelacionBancoCuentas()
        {
            CuentaParaAnidadas cuenta1 = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("2500"));
            CuentaParaAnidadas cuenta2 = new CuentaParaAnidadas("Andres", new BigDecimal("1500.8989"));

            BancoParaAnidadas banco = new BancoParaAnidadas();
            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);

            banco.setNombre("Bital");

            assertEquals(2, banco.getCuentas().size()); //probamos que banco tenga 2 cuentas relacionadas

            assertEquals("Bital", cuenta1.getBanco().getNombre());
            assertEquals("Andres", banco.getCuentas()
                    .stream()
                    .filter(c -> c.getPersona()
                    .equals("Andres"))
                    .findFirst()
                    .get()
                    .getPersona());

            assertTrue(banco.getCuentas()
                    .stream()
                    .anyMatch(c -> c.getPersona()
                    .equals("Andres")));
        }

    }
    
/************************  TEST AGRUPADOS *************************************/
    
    @Nested
    class AgrupadosTest
    {

        @Test
        void testAssertionsAgrupadas()
        {
            CuentaParaAnidadas cuenta1 = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("2500"));
            CuentaParaAnidadas cuenta2 = new CuentaParaAnidadas("Andres", new BigDecimal("1500.8989"));

            BancoParaAnidadas banco = new BancoParaAnidadas();
            banco.setNombre("Bital");

            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);

            assertAll(
                    () -> {//Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
                        assertEquals(2, banco.getCuentas().size());
                    },
                    () -> {//Con esto vamos a probar que una cuenta este relacionado a un banco
                        assertEquals("Bital", cuenta1.getBanco().getNombre());
                    },
                    () -> {//Con esto vamos a probar si dentro de las cuentas del banco tenemos la cuenta de [Andres]
                        assertEquals("Andres", banco.getCuentas()
                                .stream()
                                .filter(c -> c.getPersona()
                                .equals("Andres"))
                                .findFirst()
                                .get()
                                .getPersona());
                    },
                    () -> {//prueba casi lo mismo que el anterior pero ahora prueba si la cuenta de [Andres] esta presente
                        assertTrue(banco.getCuentas()
                                .stream()
                                .filter(c -> c.getPersona()
                                .equals("Andres"))
                                .findFirst()
                                .isPresent());
                    },
                    () -> {//prueba lo mismo que el anterior pero de otra forma
                        assertTrue(banco.getCuentas()
                                .stream()
                                .anyMatch(c -> c.getPersona()
                                .equals("Andres")));
                    }
            );

        }
    }
    
    
/************************  TEST CON MENSAJES *************************************/
    @Nested
    class MensajesTest
    {

        @Test
        void testNombreCuentaConMensaje()
        {

            //cuenta= new CuentaParaAnidadas("Andres", new BigDecimal("1000.12345"));  
            String esperado = "Andres";
            String real = cuenta.getPersona();

            assertEquals(esperado, real, "El nombre de la cuenta no es el que se espera"); //manera no recomendada
            assertTrue(real.equals("Andres"), () -> "Nombre de cuenta esperada debe ser igual a la real"); //forma correcta con lambdas
        }

        @Test
        void testAssertionsAgrupadasConMensaje()
        {
            CuentaParaAnidadas cuenta1 = new CuentaParaAnidadas("Jhon Doe", new BigDecimal("2500"));
            CuentaParaAnidadas cuenta2 = new CuentaParaAnidadas("Andres", new BigDecimal("1500.8989"));

            BancoParaAnidadas banco = new BancoParaAnidadas();
            banco.setNombre("Bital");

            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);

            assertAll(
                    () -> {//Con esto vamos a probar que el banco tenga 2 cuentas relacionadas
                        assertEquals(
                                2, banco.getCuentas().size(),
                                () -> {
                                    //esta segunda lambda es para el mensaje
                                    return "Se esperaban 2 cuentas relacionadas al banco "
                                    + banco.getNombre() + " y solo se encontraon "
                                    + banco.getCuentas().size();
                                }
                        );
                    },
                    () -> {//Con esto vamos a probar que una cuenta este relacionado a un banco
                        assertEquals(
                                "Bital", cuenta1.getBanco().getNombre(),
                                () -> {
                                    //esta segunda lambda es para el mensaje
                                    return "La cuenta1 de " + cuenta1.getPersona()
                                    + " NO esta relacionda al banco Bital, sino "
                                    + "al banco " + cuenta1.getBanco().getNombre();
                                }
                        );
                    },
                    () -> {//Con esto vamos a probar si dentro de las cuentas del banco tenemos la cuenta de [Andres]
                        assertEquals(
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
                        assertTrue(
                                banco.getCuentas()
                                        .stream()
                                        .filter(c -> c.getPersona()
                                        .equals("Andres"))
                                        .findFirst()
                                        .isPresent(),
                                () -> {
                                    //esta segunda lambda es para el mensaje
                                    return "La cuenta Andres no se encuntra en el banco "
                                    + banco.getNombre();
                                }
                        );
                    },
                    ()
                    -> //prueba lo mismo que el anterior pero de otra forma
                    assertTrue(
                            banco.getCuentas()
                                    .stream()
                                    .anyMatch(c -> c.getPersona()
                                    .equals("Andres")),
                            () -> "No existe esta cuenta en el banco " + banco.getNombre()
                    //Esta segunda lamba es para el mensaje
                    )
            );
        }
    }
    
/************************  TEST ANOTACIONES *************************************/
    @Nested
    class AnotacionesTest
    {

        @Test
        @DisplayName("Probando testAnotaciones")
        @Disabled //deshabilita la prueba, es como si se la saltara
        void testAnotaciones()
        {
            fail(); ///****** fail fuerza que falle el test, no importa si la prueba esta bien, se fuerza su fallo.
            //**** fail no es una etiqueta, esun método
            //CuentaParaAnidadas cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar

            String esperado = "Andres";
            String real = cuenta.getPersona();

            assertEquals(esperado, real);
            assertTrue(real.equals("Andres"));
        }

        //la tuive que pasar el inicio
//        @Test
//        @BeforeEach //Esto se ejecuta antes de cada método
//        void testAntesDeCada()
//        {
//            System.out.println("Iniciando prueba");
//            cuenta = new CuentaParaAnidadas("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
//        }

        @Test
        @AfterEach
        void testDespuesDeCada()
        {
            System.out.println("Finalizando prueba");
            System.out.println("");
        }

//        @Test
//        @BeforeAll
//        static void antesQueTodo()
//        {
//            System.out.println("Inicializando todas las ruebas");
//        }

//        @Test
//        @AfterAll
//        static void despuesDeTodo()
//        {
//            System.out.println("Finalizando todas las pruebas");
//        }
    }
    
    
/************************  TEST CONDICIONALES *************************************/
    @Nested
    class CondicionalesTest
    {

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows()
        {
            System.out.println("Esta prueba se realiza se activa si el S.O. es windows");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @EnabledOnOs(OS.LINUX)
        void testSololinux()
        {
            System.out.println("Esta prueba se realiza se activa si el S.O. es Linux");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @DisabledOnOs({OS.WINDOWS, OS.MAC})
        void testNoWindowsNoMac()
        {
            System.out.println("Esta prueba se desactiva si el S.O. es windows o mac");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void testOnJava8()
        {
            System.out.println("Esta prueba se activa se activa si la JRE es la 8");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @EnabledOnJre(JRE.JAVA_11)
        void testOnJava11()
        {
            System.out.println("Esta prueba se activa se activa si la JRE es la 11");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @EnabledIfSystemProperty(named = "user.language", matches = "es")
        void testOnPropertyUserLanguage()
        {
            System.out.println("Esta prueba se activa si la property user.language = es");
            assertEquals(nombreCuenta, cuenta.getPersona());
        }

        //@BeforeAll
        @Test
        void showProperties()
        {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }
    }
    
/************************  TEST ASSUMPTION (SUPOSICIONES)*************************************/
    @Nested
    @DisplayName("Assumptions")
    class AssumptionTest
    {

        @Test
        void testLanguageEs()
        {
            System.out.println("(assumption)Esta prueba se realizara solo si la property user.language = en");

            boolean verificacionIdioma = "en".equals(System.getProperty("user.language"));

            assumeTrue(verificacionIdioma);

            assertEquals(nombreCuenta, cuenta.getPersona());

            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        }

        @Test
        void testAssumptionAssumingThat()
        {
            cuenta.setBanco(new BancoParaAnidadas());
            cuenta.getBanco().setNombre("Bital");
            System.out.println("(assumingThat)Esta prueba se hara completa solo si la property user.language = en");

            boolean verificacionIdioma = "en".equals(System.getProperty("user.language"));
            assumingThat(verificacionIdioma, () -> {
                System.out.println("El lenguage es \"en\", por lo que se entro en la lambda");
                assertEquals("Bitall", cuenta.getBanco().getNombre());
            });

            assertEquals(nombreCuenta, cuenta.getPersona());

            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        }
    }


}



