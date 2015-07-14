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

        // Set the layout to the garden drawing activity xml layout
        setContentView(R.layout.activity_garden_drawing);

        // Get the GardenView class instance for modifications
        GardenView view_instance = (GardenView) findViewById(R.id.GardenView);

        // If the speciesName is not null, then this activity is being passed in the species name
        // of a plant to be added -- We need to set the Button Panel to visible and decrease the
        // width of the Garden View
        if (speciesName != null) {
            // Get the screen dimensions
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            // Get the Button panel and set it to visible
            RelativeLayout panel_instance = (RelativeLayout) findViewById(R.id.ButtonPanel);
            panel_instance.setVisibility(View.VISIBLE);

            // Get the layout parameters for the GardenView and for the Button Panel
            LayoutParams params = view_instance.getLayoutParams();
            LayoutParams panel_params = panel_instance.getLayoutParams();

            // Set the GardenView width to the screen width - the Button Panel width
            params.width = width - panel_params.width;
            view_instance.setLayoutParams(params);

            // Set the mode to ADD so the GardenView knows to render the temporary plant
            view_instance.setMode(GardenMode.ADD);
        } // Set the mode to VIEW so that the temporary plant is not rendered
        else view_instance.setMode(GardenMode.VIEW);
    }
}
