package com.cs1530_group1.gardenapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Point;
import android.view.Display;
import android.widget.RelativeLayout;
import android.view.Menu;
import android.view.MenuItem;

/**
 * GardenDrawingActivity : the graphical front end for drawing plants on top of the
 * garden layout picture.
 */
@SuppressWarnings("deprecation")
public class GardenDrawingActivity extends ActionBarActivity {
    //Log tag, for logging errors
    private static final String LOG_TAG = "GardenDrawingActivity";

    Garden g; // The garden being drawn
    String speciesName; // The species of the new plant to be added

    /**
     * onCreate : creates a new GardenView
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState)
    {
        // Get the garden so that it can be passed to the GardenView
        App app = (App)getApplication();
        g = app.getGarden();

        // Retrieve the species name of the plant being added if
        // this activity is being started from Add Plant
        speciesName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        //speciesName = "Sunflower";
        
        super.onCreate(savedInstanceState);

        // Set the layout to the garden drawing activity xml layout
        setContentView(R.layout.activity_garden_drawing);

        // Get the GardenView class instance for modifications
        GardenView view_instance = (GardenView) findViewById(R.id.GardenView);

        // If the speciesName is not null, then this activity is being passed in the species name
        // of a plant to be added -- We need to set the Button Panel to visible and decrease the
        // width of the Garden View
        if (speciesName != null) {
            // Get the screen width
            int width = getScreenWidth();

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

        } // Else: Set the mode to VIEW so that the temporary plant is not rendered
        else view_instance.setMode(GardenMode.VIEW);
    }
    

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_garden_drawing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.species_list) {
            Log.v(LOG_TAG, "Starting SpeciesListActivity");
            Intent intent = new Intent(this, SpeciesListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Acquires the width of the device screen in pixels
    private int getScreenWidth()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    // Acquires the height of the device screen in pixels
    private int getScreenHeight()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    

}
