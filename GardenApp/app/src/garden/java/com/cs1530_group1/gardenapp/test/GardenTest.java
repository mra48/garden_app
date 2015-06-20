/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Muneeb
 */
public class GardenTest {


    /**
     * Test of GardenToString method, of class Garden.
     */
    @Test
    public void testGardenToString() {
        System.out.println("GardenToString");
        String arg = "2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower";
        Garden instance = new Garden();
        String expResult = arg;
        instance.StringToGarden(arg);
        String result = instance.GardenToString();
        assertEquals(expResult, result);
    }

    /**
     * Test of StringToGarden method, of class Garden.
     */
    @Test
    public void testStringToGarden() {
        System.out.println("StringToGarden");
        String garden = "";
        Garden instance = new Garden();
        boolean expResult = true;
        boolean result = instance.StringToGarden("2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower");
        assertTrue(result);
    }

    /**
     * Test of ListSpecies method, of class Garden.
     */
    @Test
    public void testListSpecies() {
        System.out.println("ListSpecies");
        Garden instance = new Garden();
        instance.StringToGarden("2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower");
        String[] expResult = {"tomato", "sunflower"};
        String[] result = instance.ListSpecies();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of GetSpeciesInfo method, of class Garden.
     */
    @Test
    public void testGetSpeciesInfo() {
        System.out.println("GetSpeciesInfo");
        String species = "tomato";
        Garden instance = new Garden();
        instance.StringToGarden("2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower");
        String expResult = "tomato:half:23:44:1234";
        String result = instance.GetSpeciesInfo(species);
        assertEquals(expResult, result);
    }

    /**
     * Test of HasPlant method, of class Garden.
     */
    @Test
    public void testHasPlant_Plant() {
        System.out.println("HasPlant");
        Species s = new Species("sunflower", "full", 34, 54, 1245);
        Plant p = new Plant(2,3,s);
        Garden instance = new Garden();
        instance.StringToGarden("2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower");
        boolean expResult = true;
        boolean result = instance.HasPlant(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of HasPlant method, of class Garden.
     */
    @Test
    public void testHasPlant_int_int() {
        System.out.println("HasPlant");
        int x = 0;
        int y = 0;
        Garden instance = new Garden();
        instance.StringToGarden("2:tomato:half:23:44:1234:sunflower:full:34:54:1245:1:2:3:sunflower");
        boolean expResult = true;
        boolean result = instance.HasPlant(2, 3);
        assertEquals(expResult, result);
    }
    
}
