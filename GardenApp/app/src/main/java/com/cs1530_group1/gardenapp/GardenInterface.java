package com.cs1530_group1.gardenapp;

import java.util.*;

/**
 *
 * @author Muneeb
 */
public interface GardenInterface {
        
    //Methods for second sprint
    
//    String GardenToString(); //returns garden as serialized string
//    boolean StringToGarden(String garden); //used to initizlize or update garden (boolean used by other classes to check if function performed as expected)
//	
//    String[] ListSpecies(); //returns an array of the names of all species or null if no species have been created yet
//    String GetSpeciesInfo(String species); //returns a string of a single species info in the format tomato:sunValue:pruneValue:waterValue:fertilizeValue or null if species does not exist
//
//    boolean HasPlant(Plant p); //return true if Plant p exists in the garden
//    boolean HasPlant(int x, int y); //returns true if a plant (any plant) is located at x,y 


//*************************************************************************************************************
    //methods for third sprint
    
    ArrayList<Plant> getPlantList();
    String[] getSpeciesNames();
    Species getSpeciesInfo(String speciesName);
    
    boolean addPlant(int x, int y, String speciesName);
    boolean removePlant(int x, int y);
    boolean movePlant(int oldx, int oldy, int newx, int newy);
    
    boolean addSpecies(String speciesName); 
    boolean removeSpecies(String speciesName); //remember to also remove associated plants in garden.java

    boolean setDescription(String speciesName, String des);
    boolean setSpeciesType(String speciesName,String speciesType);
    boolean setPlantDate(String speciesName, Date plantDate);
    boolean setPruneDate(String speciesName, Date pruneDate);
    boolean setSunLevel(String speciesName, String sunLevel);
    boolean setColor(String speciesName, int color);
    boolean setSize(String speciesName, int size);

    String getDescription(String speciesName);
    String getSpeciesType(String speciesName); //the species type will be 0 for annual, 1 for perennial, and 2 for tree
    Date getPlantDate(String speciesName);
    Date getPruneDate(String speciesName);
    String getSunLevel(String speciesName);
    int getColor(String speciesName);
    int getSize(String speciesName);
    
    	
            
}
