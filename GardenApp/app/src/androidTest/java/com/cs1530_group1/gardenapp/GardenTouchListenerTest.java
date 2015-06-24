package com.cs1530_group1.gardenapp;

import junit.framework.TestCase;

/**
 * Created by Glenn on 6/23/15.
 */
public class GardenTouchListenerTest extends TestCase {

    /**
     * Test a change of ten in the negative x direction when the background is at x = 0 and the background
     * is 1155 wide and the screen is 1080 wide
     * Change should be -10, this is the happy case
     * @throws Exception
     */
    public void testGetBackgroundChange1() throws Exception {
        int change = GardenView.getBackgroundChange(-10, 0, 1155, 1080);

        assertTrue(change == -10);
    }

    /**
     * Tests trying to move the background positive 10 units when the background is at 0
     * The background should not move in this case.
     * @throws Exception
     */
    public void testGetBackgroundChange2() throws Exception {
        int change = GardenView.getBackgroundChange(10, 0, 1155, 1080);

        assertTrue(change == 0);
    }

    /**
     * Tests trying to move the background positive -10 units when the right edge of the background
     * is at the right edge of the screen. It should not be able to move negative here.
     * The background should not move in this case.
     * @throws Exception
     */
    public void testGetBackgroundChange3() throws Exception {
        int change = GardenView.getBackgroundChange(-10, -75, 1155, 1080);

        assertTrue(change == 0);
    }
}