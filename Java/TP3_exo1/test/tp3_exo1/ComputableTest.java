/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3_exo1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Alexa
 */
public class ComputableTest {
    
    public ComputableTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of compute method, of class Computable.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        double f = 0.0;
        double s = 0.0;
        Computable instance = new ComputableImpl();
        double expResult = 0.0;
        double result = instance.compute(f, s);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ComputableImpl implements Computable {

        public double compute(double f, double s) {
            return 0.0;
        }
    }
    
}
