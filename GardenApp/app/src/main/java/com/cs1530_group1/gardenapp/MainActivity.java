package com.cs1530_group1.gardenapp;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;

/**
 * The MainActivity of an app is the screen that is first loaded upon a user opening an app.
 *
 * @author Charles Smith <cas275@pitt.edu>
 */
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

    //Log tag, for logging errors
    private static final String LOG_TAG = "Main Activity";

    /**
     *  This string should be used to create the garden when a garden cannot be loaded.
     */
    private static final String DEFAULT_GARDEN_STRING = "2-tomato-a tomato species-Annual-01/23/1993 23:25:12-03/03/1993 21:22:13-high-25-33-sunflower-a sunny flower-Perennial-02/21/1986 22:42:12-04/12/1980 23:45:12-low-32-12-1-23-43-tomato";

    /**
     *
     * This method is called upon the user going to the main screen.
     * This screen welcomes the user, and shows navigation options.
     *
     * While the user is clicking buttons, the garden data structure is being loaded/created
     *
     * @param savedInstanceState used for super.onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading the garden
        App app = (App)getApplication();
        setupGarden(app);


    }

    /**
     * this checks the status of garden and either does nothing, loads the garden, or creates a new default garden
     * depending on the app state
     *
     * @param app holds the global state of the application
     */
    private void setupGarden(App app){
        Log.v(LOG_TAG, "checking status of garden");

        if(app.getGarden()==null) { //the garden hasn't been loaded yet
            Log.v(LOG_TAG, "garden not yet loaded");
            if (FileOperation.exists(App.SAVEFILE_NAME)){

                Log.v(LOG_TAG, "save file exists");
                if (!loadGarden(app)) { //try to load the garden.
                    Log.wtf(LOG_TAG, "file exists, but cannot be read"); //this shouldn't happen
                    loadDefaultGarden(app); //if the garden doesn't load loads the default garden
                }
            }
            else{
                /* save file does not exist */
                Log.v(LOG_TAG,"save file does not yet exist");
                loadDefaultGarden(app);
            }
        }
        else{
            Log.v(LOG_TAG, "garden has already been loaded");
        }
    }

    /**
     * Loads the garden using FileOperation
     *
     * @param app the class holding the 'globals' of the application
     * @return true if garden is loaded, false otherwise
     */
    private boolean loadGarden(App app){
        Log.d(LOG_TAG, "garden not yet initialized, loading from save");
        String gardenString;
        try {
            gardenString = FileOperation.load(App.SAVEFILE_NAME);
            app.setGarden(Garden.stringToGarden(gardenString));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * This class sets the garden based upon the default garden string
     *
     * @param app the object holding the 'globals' of the application
     */
    private void loadDefaultGarden(App app){
        Log.v(LOG_TAG, "loading deafualt garden");
        app.setGarden(Garden.stringToGarden(DEFAULT_GARDEN_STRING));

        /* try to save the new garden */
        try{
            FileOperation.save(App.SAVEFILE_NAME,DEFAULT_GARDEN_STRING);
            Log.v(LOG_TAG, "saving default garden");
        }
        catch (IOException e){
            Log.e(LOG_TAG,"unable to save default garden", e); //log the error
            Toast.makeText(getApplicationContext(),"There was a problem saving the garden", Toast.LENGTH_SHORT).show(); //alert the user
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
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
