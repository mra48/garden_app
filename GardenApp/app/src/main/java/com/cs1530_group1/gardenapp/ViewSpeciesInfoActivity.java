package com.cs1530_group1.gardenapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This activity displays the information about a selected species (passed in with the EXTRA_TEXT from Intent)
 *
 * The hierarchical parent is set to SpeciesListActivity, so that is what the 'up' button launches.
 * 
 * @author Charles Smith <cas275@pitt.edu>
 */

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
        String nameString = "<b>Name:</b><br />"+speciesName;
        name.setText(Html.fromHtml(nameString)); //Html.fromHtml is needed to set bold strings

        //get the description TextView and append info
        TextView descrpit = (TextView)findViewById(R.id.species_descript);
        String descriptString = "<b>Description:</b><br />" + garden.getDescription(speciesName);
        descrpit.setText(Html.fromHtml(descriptString));

        //set type TextView
        TextView type = (TextView)findViewById(R.id.species_type);
        String typeString = "<b>Type:</b><br />" + garden.getSpeciesType(speciesName);
        type.setText(Html.fromHtml(typeString));

        //set sun TextView
        TextView sun = (TextView)findViewById(R.id.species_sun);
        String sunString = "<b>Sun Level:</b><br />" + garden.getSunLevel(speciesName);
        sun.setText(Html.fromHtml(sunString));

        //set plant date TextView
        TextView plantDate = (TextView)findViewById(R.id.species_plant_date);
        String plantDateString = "<b>Plant Date:</b><br />"+garden.getPlantDate(speciesName);
        plantDate.setText(Html.fromHtml(plantDateString));

        //set prune date TextView
        TextView pruneDate = (TextView)findViewById(R.id.species_prune_date);
        String pruneDateString = "<b>Prune Date:</b><br />"+garden.getPruneDate(speciesName);
        pruneDate.setText(Html.fromHtml(pruneDateString));

        //set size TextView
        TextView size = (TextView)findViewById(R.id.species_size);
        String sizeString = "<b>Size:</b><br />"  + garden.getSize(speciesName);
        size.setText(Html.fromHtml(sizeString));

        //set color TextView
        TextView color = (TextView)findViewById(R.id.species_color);

        String colorString = "<b>Color:</b><br />"
                + "<font color=\"0x" + String.format("%06x", garden.getColor(speciesName)) +"\">"  //results to <font color="0xCOLOR">
                + "0x" +  String.format("%06x",garden.getColor(speciesName)) //results to 0xCOLOR
                + "</font>";
        
        color.setText(Html.fromHtml(colorString));



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
}
