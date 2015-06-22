package com.cs1530_group1.gardenapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The Activity Fragment that creates a ListView of all the species.
 * @author Charles Smith <cas275@pitt.ecu>
 */
public class SpeciesListActivityFragment extends Fragment {

    private ArrayAdapter adapter; //this is needed to populate the listview
    private static final String LOG_TAG = "SpeciesListActivityF..."; //for when something needs logged.

    public SpeciesListActivityFragment() {
        //nothing here!
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_species_list, container, false);


        //TODO replace this
        //fake data is to be used only until the real data is loaded.
        Log.w(LOG_TAG, "many features are missing and/or populated with fake data");

        String[] fakeData = {
                "Tomato",
                "Mint",
                "Red flower",
                "Trees",
                "Medicinal herbs",
                "Another kind of Tree",
                "That yellow one nobody likes",
                "Smaller Tomatoes",
                "Cherry Tomatoes",
                "Smaller Cherry Tomatoes",
                "Another item so the fake data scrolls on my large screen",
                "Are computers a kind of plant?",
                "What about Android phones?",
                "Really, they're not?",
                "Huh, TIL",
                "Money tree"
        };


        


        ArrayList<String> fakeDataList = new ArrayList<>();
        //data needs to be in a list, I only made a string[] to make creating the fake data a little easier
        Collections.addAll(fakeDataList, fakeData);

        adapter = new ArrayAdapter(getActivity(), R.layout.species_list_textview, R.id.list_item_species_textview,fakeDataList);


        ListView speciesList = (ListView) rootView.findViewById(R.id.listview_species);

        speciesList.setAdapter(adapter);
        Log.d(LOG_TAG, "populated list with fake data");

        //kinda gross but i'm makeing a class inside an argument
        speciesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            /**
             * is called upon a click in the speciesList
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo make this bring up a dialog box
                //for now it's just going to be a toast to see if it was clicked
                Toast.makeText(getActivity(), (String) adapter.getItem(position), Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, "*click*");
            }
        });


        return rootView;
    }

    //TODO create an async task to get the species list and have it update the list upon completion
}
