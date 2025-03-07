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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@TestInstance(Lifecycle.PER_CLASS)
public class CuentaParaAnidadasTest
{
    Cuenta cuenta;
    String nombreCuenta= "Andres";
    
    @AfterAll
    void iniciar()
    {
        CuentaParaAnidadasTest c= new CuentaParaAnidadasTest();
    }
    
    @Test
    @BeforeEach
    void inicilizarCuenta()
    {
        System.out.println("Inicilizando cuenta ....");
        this.cuenta= new Cuenta("Andres", new BigDecimal("1000.12345"));  //se instancia un objeto de la clas a probar
    }
    
    @Test
    void testNombreCuenta()
    {
        String esperado= "Andres";
        String real= cuenta.getPersona();
        
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Andres"));
    }
    
    @Test
    void testSaldoCuenta()
    {
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) <0);
    }
    
    @Test
    void testIgualdadCuenta()
    {
        cuenta= new Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor real
        Cuenta cuenta2= new Cuenta("Jhon Doe", new BigDecimal("8900.997")); //valor esperado

        Assertions.assertEquals(cuenta, cuenta2);
    }
    
    @Test
    void testDebitoCuenta()
    {
        cuenta.debito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
        Assertions.assertEquals(900, cuenta.getSaldo().intValue()); //si a 1000.12345 le restamos 100 y lo hacemos int debe dar 900
        Assertions.assertEquals("900.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
    }
    
    @Test
    void testCreditoCuenta()
    {
        cuenta.credito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo()); //primeo se verifica que el saldo de la cuenta no sea null (aunque esta revisión no sea explícita se hace en la linea de abajo pues no serían igual los valores)
        Assertions.assertEquals(1100, cuenta.getSaldo().intValue()); //si a 1000.12345 le sumamos 100 y lo hacemos int debe dar 1100
        Assertions.assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); //se va revisar que sea igual, pero con String en lugar de int
    }
    
    @Test
    void testDineroInsuficienteException()
    {
        Exception exception= Assertions.assertThrows(DineroInsuficienteException.class, ()->{
            cuenta.debito(new BigDecimal(15000));
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
    
    @Test
    void testRelacionBancoCuentas()
    {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2= new Cuenta("Andres", new BigDecimal("1500.8989"));
        
        Banco banco= new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        
        banco.setNombre("Bital");
        
        Assertions.assertEquals(2, banco.getCuentas().size()); //probamos que banco tenga 2 cuentas relacionadas
        
        Assertions.assertEquals("Bital", cuenta1.getBanco().getNombre());

        Assertions.assertEquals("Andres", banco.getCuentas()
                                                            .stream()
                                                            .filter(c -> c.getPersona()
                                                            .equals("Andres"))
                                                            .findFirst()
                                                            .get()
                                                            .getPersona());
        
        Assertions.assertTrue(banco.getCuentas()
                                   .stream()
                                   .anyMatch(c -> c.getPersona()
                                   .equals("Andres")));
    }
    
    
    @Nested
    class SistemaOperativoTest
    {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void TestSoloWindows()
        {
            System.out.println("Solo se entra al test si se usa windows");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }
        
        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void TestSoloLinuxYMac()
        {
            System.out.println("Solo se entra al test si se usa Linux o Mac");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }
        
        @Test
        @DisabledOnOs(OS.LINUX)
        void TestSeSaltaEnLinux()
        {
            System.out.println("Esta prueba se salta si estamos en LINUX");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }
        
    }
    
    @Nested
    class JavaVersionTest
    {
         
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void testOnJava8()
        {
            System.out.println("Esta prueba se activa se activa si la JRE es la 8");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }

        @Test
        @EnabledOnJre(JRE.JAVA_11)
        void testOnJava11()
        {
            System.out.println("Esta prueba se activa se activa si la JRE es la 11");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }
        
        @Test
        @EnabledOnJre(JRE.JAVA_15)
        void testOnJava15()
        {
            System.out.println("Esta prueba se activa se activa si la JRE es la 15");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }

    }
    
    @Nested
    class SistemPropertiesTest
    {
        /**
         * No es un test como tal, solo es para mostrar las propertyes que tiene java
         * java
         */
        @BeforeAll
        @Test
        void showProperties()
        {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }

        @Test
        @EnabledIfSystemProperty(named = "user.language", matches = "es")
        void testOnPropertyUserLanguage()
        {
            System.out.println("Esta prueba se activa si la property user.language = es");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }
        
        @Test
        @DisabledIfSystemProperty(named = "user.country", matches = "MX")
        void testOnPropertyUserCountry()
        {
            System.out.println("Esta prueba se salta si la property user.country = MX");
            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());
        }

    }
    
    @Nested
    class AssumptionsTest
    {

        @Test
        void testLanguageEs()
        {
            System.out.println("(assumption)Esta prueba se realizara solo si la property user.language = en");

            boolean verificacionIdioma = "en".equals(System.getProperty("user.language"));

            Assumptions.assumeTrue(verificacionIdioma);

            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());

            Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        }

        @Test
        void testAssumptionAssumingThat()
        {
            cuenta.setBanco(new Banco());
            cuenta.getBanco().setNombre("Bital");
            
            System.out.println("(assumingThat)Esta prueba se hara completa solo si la property user.language = en");

            boolean verificacionIdioma = "en".equals(System.getProperty("user.language"));

            Assumptions.assumingThat(verificacionIdioma, () -> {
                System.out.println("El lenguage es \"en\", por lo que se entro en la lambda");
                Assertions.assertEquals("Bitall", cuenta.getBanco().getNombre());
            });

            Assertions.assertEquals(nombreCuenta, cuenta.getPersona());

            Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        }
    }
    
}
