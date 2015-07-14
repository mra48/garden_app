package com.cs1530_group1.gardenapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Point;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * GardenDrawingActivity : the graphical front end for drawing plants on top of the
 * garden layout picture.
 */

public class GardenDrawingActivity extends Activity {

    private static final String LOG_TAG = "GardenDrawingActivity"; //for when something needs logged.
    Garden g;
    String speciesName;
    /**
     * onCreate : creates a new GardenView
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState)
    {
        // Get the garden so that it can be passed to the GardenView
        App app = (App)getApplication();
        g = app.getGarden();
        speciesName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        speciesName = "Sunflower";

        super.onCreate(savedInstanceState);
        //setContentView(new GardenView(this, g));

        setContentView(R.layout.activity_garden_drawing);

        GardenView view_instance = (GardenView) findViewById(R.id.GardenView);

        if (speciesName != null) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;


            RelativeLayout panel_instance = (RelativeLayout) findViewById(R.id.ButtonPanel);
            panel_instance.setVisibility(View.VISIBLE);

            LayoutParams params = view_instance.getLayoutParams();
            LayoutParams panel_params = panel_instance.getLayoutParams();


            Log.v(LOG_TAG, "panel Width " + panel_params.width + " panel Height " + panel_params.height);

            Log.v(LOG_TAG, "Width " + params.width + " Height " + params.height);
            params.width = width - panel_params.width;
            //params.height=height;
            view_instance.setLayoutParams(params);

            Log.v(LOG_TAG, "Width " + params.width + " Height " + params.height);

            view_instance.setMode(GardenMode.ADD);
        }
        else view_instance.setMode(GardenMode.VIEW);
    }
}
