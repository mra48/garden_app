package com.cs1530_group1.gardenapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * GardenDrawingActivity : the graphical front end for drawing plants on top of the
 * garden layout picture.
 */
@SuppressWarnings("deprecation")
public class GardenDrawingActivity extends ActionBarActivity {
   //Log tag, for logging errors
   private static final String LOG_TAG = "Garden Drawing Activity";

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

}