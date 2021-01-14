/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdvmanager;

import java.io.PrintStream;
import java.time.LocalDateTime;
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
public class RdvListTest {
    
    public RdvListTest() {
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
     * Test of getQuota method, of class RdvList.
     */
    @Test
    public void testGetQuota() {
        System.out.println("getQuota");
        RdvList instance = new RdvList();
        int expResult = 1000;
        int result = instance.getQuota();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setQuota method, of class RdvList.
     */
    @Test
    public void testSetQuota() {
        System.out.println("setQuota");
        int aQuota = 0;
        RdvList instance = new RdvList();
        boolean expResult = false;
        boolean result = instance.setQuota(aQuota);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class RdvList.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Rdv aRdv = null;
        RdvList instance = new RdvList();
        boolean expResult = false;
        boolean result = instance.add(aRdv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printFrom method, of class RdvList.
     */
    @Test
    public void testPrintFrom() {
        System.out.println("printFrom");
        PrintStream output = null;
        LocalDateTime d = null;
        RdvList instance = new RdvList();
        instance.printFrom(output, d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveCSV method, of class RdvList.
     */
    @Test
    public void testSaveCSV() {
        System.out.println("saveCSV");
        PrintStream output = null;
        RdvList instance = new RdvList();
        instance.saveCSV(output);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
