package com.cs1530_group1.gardenapp;

import junit.framework.TestCase;

/**
 * Created by root on 6/23/15.
 */
public class GardenTouchListenerTest extends TestCase {

    /**
     * Test a change of ten in the x direction when the background is at x = 0 and the background
     * is 1155 wide and the screen is 1080 wide
     * Change should be 10, this is the happy case
     * @throws Exception
     */
    public void testGetBackgroundChange() throws Exception {
        int change = GardenView.getBackgroundChange(10, 0, 1155, 1080);

        assertTrue(change == 10);
    }
}