package com.cs1530_group1.gardenapp;

import android.app.Application;

/**
 * This class holds the 'global' variables that are needed across multiple Activities
 * It is considered good practice to use 'getters and setters' instead of making the
 * variable public.
 *
 * The methods can be called by first getting the instance of this
 * <code>App app = (App)getApplicationContext();</code>
 * Then calling the needed method
 * <code>app.getVar();</code>
 * or
 * <code>app.setVar(var);</code>
 *
 * @author Charles Smith <cas275@pitt.edu>
 */
public class App extends Application{
    private Garden garden;

    /**
     * the string filename used for saving/loading
     */
    public static final String SAVEFILE_NAME = "Garden-Save.dat";


    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }
}
