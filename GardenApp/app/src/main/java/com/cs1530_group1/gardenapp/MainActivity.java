package com.cs1530_group1.gardenapp;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

    //Log tag, for logging errors
    private static final String LOG_TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here seems like a good place to create the garden
        App app = (App)getApplication();

        //todo replace this with a call to storage to get the saved garden/create a new one
        //start fake data
        String gardenString = "2-tomato-a tomato species-Annual-01/23/1993 23:25:12-03/03/1993 21:22:13-high-25-33-sunflower-a sunny flower-Perennial-02/21/1986 22:42:12-04/12/1980 23:45:12-low-32-12-1-23-43-tomato";
        //end fake data
        //todo this shouldn't happen in the main thread
        Garden garden = Garden.stringToGarden(gardenString);
        app.setGarden(garden);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * This is to launch the activity that views all the Species as a ListView
     *
     * @param view the view that called this method (currently unused)
     */
    public void startSpeciesListActivity(View view){

        Log.v(LOG_TAG, "Starting SpeciesListActivity");
        Intent intent = new Intent(this, SpeciesListActivity.class);
        startActivity(intent);

        /*
        Log.e(LOG_TAG, "startSpeciesListActivity is not yet implemented");
        Toast.makeText(getApplicationContext(), "This feature is not yet implemented", Toast.LENGTH_SHORT).show();
        */
    }

    /**
     * This method is called upon a button click from the activity_main screen, and is to launch
     * the view of the garden.
     *
     * @param view the view that called this method (currently unused)
     */
    //todo rename this
    public void startGardenViewActivity(View view){

        Log.v(LOG_TAG, "starting GardenDrawingActivity");
        Intent intent = new Intent(this, GardenDrawingActivity.class);
        startActivity(intent);

        /*
        Log.e(LOG_TAG, "startGardenViewActivity is not yet implemented");
        Toast.makeText(getApplicationContext(), "This feature is not yet implemented", Toast.LENGTH_SHORT).show();
        */
    }
}
