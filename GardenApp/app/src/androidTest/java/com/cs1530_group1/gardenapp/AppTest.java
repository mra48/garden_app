package com.cs1530_group1.gardenapp;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by cas on 6/22/15.
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