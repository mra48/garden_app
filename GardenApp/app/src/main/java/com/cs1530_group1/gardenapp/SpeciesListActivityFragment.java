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



/**
 * The Activity Fragment that creates a ListView of all the species.
 * @author Charles Smith <cas275@pitt.ecu>
 */
public class SpeciesListActivityFragment extends Fragment {

    private ArrayAdapter<String> adapter; //this is needed to populate the listview
    private static final String LOG_TAG = "SpeciesListActivityF..."; //for when something needs logged.

    public SpeciesListActivityFragment() {
        //nothing here!
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_species_list, container, false);


        /* getting the String[] of all species */
        String[] gardenSpeciesList;
        App app = (App) getActivity().getApplicationContext();
        Garden garden = app.getGarden();
        gardenSpeciesList = garden.getSpeciesNames();



        ArrayList<String> dataList = new ArrayList<>();
        //data needs to be in a list
        Collections.addAll(dataList, gardenSpeciesList);

        adapter = new ArrayAdapter<>(getActivity(), R.layout.species_list_textview, R.id.list_item_species_textview,dataList);


        ListView speciesList = (ListView) rootView.findViewById(R.id.listview_species);

        speciesList.setAdapter(adapter);



        //kinda gross but i'm making a class inside an argument
        //this is appears to be standard practice to do this, so I may be crazy, but so is everyone else
        speciesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            /**
             * is called upon a click in the speciesList
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo make this bring up a dialog box
                //for now it's just going to be a toast to see if it was clicked
                Toast.makeText(getActivity(),  adapter.getItem(position), Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, "*click*");
            }
        });


        return rootView;
    }


}
