package com.cs1530_group1.gardenapp;

import android.app.Activity;
import android.test.ActivityTestCase;

/**
 * Created by root on 7/4/15.
 */
public class GardenViewTest extends ActivityTestCase {

    public void testLoadBitmapImage() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
    }


}