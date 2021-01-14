/*
 * Copyright (C) 2019 EIDD 2A SIE
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package rdvmanager.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @version 0.1
 * @author M. Sighireanu
 * @author YOU
 */
public class RdvTest {

    private Rdv instance1 = new Rdv("Dentist",
            LocalDate.of(2019, 10, 1),
            LocalTime.of(12, 0), 60);

    public RdvTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDescription method, of class Rdv.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Rdv instance = instance1;
        String expResult = "Dentist";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStart method, of class Rdv.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        Rdv instance = instance1;
        LocalDateTime expResult = LocalDateTime.of(2019, 10, 1, 12, 0);
        LocalDateTime result = instance.getStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Rdv.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Rdv instance = instance1;
        String expResult = "2019-10-01 12:00:00 Dentist";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
