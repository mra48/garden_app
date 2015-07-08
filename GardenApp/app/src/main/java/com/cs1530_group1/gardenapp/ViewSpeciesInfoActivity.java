package com.cs1530_group1.gardenapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private String speciesName;


    @Override
    /**
     * This method is called upon this activity being started.
     *
     * In here the screen is setup to display all the user-relevant information of a species.
     * This is done through several HTML encoded TextView s
     * Html.fromHtml(String) is used to parse the HTML encoding
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_species_info);



        //get the speciesName passed from the last activity
        speciesName = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        //if no species name is received, alert user and log the error.
        if(speciesName == null||speciesName.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Error loading information", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "received a null or empty string, cannot display relevant information");
            return;  //stop trying, nothing was received.
        }


        updateTextViews();


    }

    /**
     * this updates the TextViews displayed to the user with the correct information
     *
     */
    protected void updateTextViews(){

        //get the garden from App class
        Garden garden = ((App) getApplication()).getGarden();

        //get the name TextView and append the species name
        String nameString = "<b>Name:</b><br />"+speciesName;
        setText(R.id.species_name,nameString);

        //get the description TextView and append info
        String descriptString = "<b>Description:</b><br />" + garden.getDescription(speciesName);
        setText(R.id.species_descript,descriptString);

        //set type TextView
        String typeString = "<b>Type:</b><br />" + garden.getSpeciesType(speciesName);
        setText(R.id.species_type,typeString);

        //set sun TextView
        String sunString = "<b>Sun Level:</b><br />" + garden.getSunLevel(speciesName);
        setText(R.id.species_sun,sunString);

        //set plant date TextView
        String plantDateString = "<b>Plant Date:</b><br />"+garden.getPlantDate(speciesName);
        setText(R.id.species_plant_date,plantDateString);

        //set prune date TextView
        String pruneDateString = "<b>Prune Date:</b><br />"+garden.getPruneDate(speciesName);
        setText(R.id.species_prune_date,pruneDateString);

        //set size TextView
        String sizeString = "<b>Size:</b><br />"  + garden.getSize(speciesName);
        setText(R.id.species_size,sizeString);

        //set color TextView
        String colorString = "<b>Color:</b><br />"
                //+ "<font color=\"0x" + String.format("%06x", garden.getColor(speciesName)) +"\">"  //results to <font color="0xCOLOR">
                + "0x" +  String.format("%06x",garden.getColor(speciesName)) //results to 0xCOLOR
                //+ "</font>"
                ;
        setText(R.id.species_color,colorString);

    }

    /**
     * updates the html parsed text of id
     * @param id the id of a TextView
     * @param htmlString the String to go into that View
     */
    protected void setText(int id, String htmlString){
        TextView view = (TextView)findViewById(id);
        view.setText(Html.fromHtml(htmlString));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;  //not inflating the menu and returning false removes the menu from the activity
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
     * called when a user clicks on the 'remove' button
     *
     * this should remove the current species from the garden, and then launch the list species activity.
     * @param view unused
     */
    public void removeSpecies(View view) {
        
        ((App)getApplication()).getGarden().removeSpecies(speciesName);  //removes the species
        Intent intent = new Intent(this,SpeciesListActivity.class);
        startActivity(intent); // goes back to the list, as the data on this screen has been deleted
    }

    /**
     * called when a user clicks on the 'edit' button
     * @param view unused
     */
    public void editInfo(View view) {
        /* logs and alerts that edit is not yet ready for the user */
        Log.e(LOG_TAG, "edit info is not yet implemented");
        Toast.makeText(getApplicationContext(),"This feature is not yet implemented", Toast.LENGTH_SHORT).show();
    }

    /**
     * called when a user clicks on the 'add to garden' button
     * @param view unused
     */
    public void addToGarden(View view) {
        /* logs and alerts that add is not yet ready for the user */
        Log.e(LOG_TAG, "add to Garden is not yet Implemented");
        Toast.makeText(getApplicationContext(),"This feature is not yet implemented", Toast.LENGTH_SHORT).show();
        //all this will need to do is create & launch an intent with the species name bundled in for glenn's GardenDrawingActivity, when he's ready for it
    }
}
