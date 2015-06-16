package com.cs1530_group1.gardenapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class SpeciesListActivityFragment extends Fragment {

    ArrayAdapter adapter;

    public SpeciesListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_species_list, container, false);


        //TODO replace this
        //fake data is to be used only until the real data is loaded.
        String[] fakeData = {
                "Tomato",
                "Mint",
                "Red flower",
                "Trees",
                "Medicinal herbs",
                "Money tree"
        };

        ArrayList<String> fakeDataList = new ArrayList<String>();
        //data needs to be in a list, I only made a string[] to make creating the fake data a little easier
        for(String s:fakeData){
            fakeDataList.add(s);
        }

        adapter = new ArrayAdapter(getActivity(), R.layout.species_list_textview, R.id.list_item_species_textview,fakeDataList);

        ListView speciesList = (ListView) rootView.findViewById(R.id.listview_species);

        speciesList.setAdapter(adapter);


        return rootView;
    }
}
