package com.cs1530_group1.gardenapp;

import junit.framework.TestCase;

/**
 * @author Charles Smith
 */
public class MainActivityTest extends TestCase {

    /**
     * Tests if the JUnit will run
     */

    public void testExists(){
        assertTrue(true);
    }

    public void testLoadGarden() throws Exception {
        String testString = "2-tomato-a tomato species-Annual-01/23/1993 11:25:12-03/03/1993 09:22:13-high-25-33-sunflower-a sunny flower-Perennial-02/21/1986 10:42:12-04/12/1980 11:45:12-low-32-12-1-23-43-tomato";
        FileOperation.save("TestSave", testString);
        MainActivity activity = new MainActivity();
        App app = new App();
        activity.loadGarden(app);
        Garden g = Garden.stringToGarden(testString);

        assertEquals("tomato",g.getSpeciesNames()[0]);
        assertEquals("sunflower",g.getSpeciesNames()[1]);

    }

    public void testLoadDefaultGarden() throws Exception {
        String testString = "2-tomato-a tomato species-Annual-01/23/1993 11:25:12-03/03/1993 09:22:13-high-25-33-sunflower-a sunny flower-Perennial-02/21/1986 10:42:12-04/12/1980 11:45:12-low-32-12-1-23-43-tomato";
        MainActivity activity = new MainActivity();
        App app = new App();
        activity.loadDefaultGarden(app);
        Garden g = app.getGarden();

        assertEquals("tomato",g.getSpeciesNames()[0]);
        assertEquals("sunflower", g.getSpeciesNames()[1]);
    }
}