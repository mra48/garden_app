package com.cs1530_group1.gardenapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;




@SuppressWarnings("deprecation")
public class ViewSpeciesInfoActivity extends ActionBarActivity {

    private static final String LOG_TAG = "Species Info Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_species_info);

        //get the garden from App class
        Garden garden = ((App) getApplication()).getGarden();

        //get the speciesName passed from the last activity
        String speciesName = getIntent().getStringExtra(Intent.EXTRA_TEXT);


        if(speciesName == null||speciesName.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Error loading information", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "received a null or empty string, cannot display relevant information");
            return;
        }

        //get the name TextView and append the species name
        TextView name = (TextView) findViewById(R.id.species_name);
        name.append("\n" + speciesName);

        //get the description TextView and append info
        TextView descrpit = (TextView)findViewById(R.id.species_descript);
        descrpit.append("\n" + garden.getDescription(speciesName));

        //set type TextView
        TextView type = (TextView)findViewById(R.id.species_type);
        type.append("\n" + garden.getSpeciesType(speciesName));

        //set sun TextView
        TextView sun = (TextView)findViewById(R.id.species_sun);
        sun.append("\n" + garden.getSunLevel(speciesName));

        //set plant date TextView
        TextView plantDate = (TextView)findViewById(R.id.species_plant_date);
        plantDate.append("\n" + garden.getPlantDate(speciesName));

        //set prune date TextView
        TextView pruneDate = (TextView)findViewById(R.id.species_prune_date);
        pruneDate.append("\n"+garden.getPruneDate(speciesName));

        //set size TextView
        TextView size = (TextView)findViewById(R.id.species_size);
        size.append("\n" + garden.getSize(speciesName));

        //set color TextView
        TextView color = (TextView)findViewById(R.id.species_color);
        color.append("\n" + Integer.toHexString(garden.getColor(speciesName)));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_species_info, menu);
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
}
