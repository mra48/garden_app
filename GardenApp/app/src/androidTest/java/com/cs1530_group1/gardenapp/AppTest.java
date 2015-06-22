package com.cs1530_group1.gardenapp;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * @author Charles Smith <cas275@pitt.edu>
 */
public class AppTest extends TestCase {

    @SmallTest
    public void test(){
        Garden garden = new Garden();
        App app = new App();
        app.setGarden(garden);
        assertEquals(garden, app.getGarden());
    }
}