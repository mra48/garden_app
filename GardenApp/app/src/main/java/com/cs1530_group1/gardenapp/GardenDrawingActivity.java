package com.cs1530_group1.gardenapp;
import android.app.Activity;
import android.os.Bundle;

/**
 * GardenDrawingActivity : the graphical front end for drawing plants on top of the
 * garden layout picture.
 */
public class GardenDrawingActivity extends Activity {

    /**
     * onCreate : creates a new GardenView
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState)
    {
        // Get the garden so that it can be passed to the GardenView
        App app = (App)getApplication();
        Garden g = app.getGarden();

        super.onCreate(savedInstanceState);
        setContentView(new GardenView(this, g));
    }
}
