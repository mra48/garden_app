package com.cs1530_group1.gardenapp;

import java.util.*;

/**
 *
 * @author Muneeb
 */
public interface GardenInterface {
        
	/**
		This function returns a copy of the plantList ArrayList	
		
		@return A copy of the plantList ArrayList
	*/		
    ArrayList<Plant> getPlantList();

    /**
		This function returns an array containing all of the speciesNames currently in the species list	
		
		@return an array containing all of the speciesNames currently in the species list	
	*/			
    String[] getSpeciesNames();
    
    /**
		This function returns a species object based on its name
		
		@param speciesName the name of the species whose object is desired
		@return a copy of the species object
	*/		
	Species getSpeciesInfo(String speciesName);
    
    /**
		This function adds a plant to the Garden
		
		@param speciesName the name of the species that will be planted as a plant
		@param x the desired x location of the plant
		@param y the desired y location of the plant
		@return true if the plant was added successfully, false if the species does not exist in the list
	*/		
    boolean addPlant(int x, int y, String speciesName);
    /**
		This function removes a plant from the garden
		
		@param x the x location of the plant to be removed
		@param y the y location of the plant to be removed
		@return true if the plant was removed successfully, false if the plant is not in the garden
	*/		
    boolean removePlant(int x, int y);
    /**
		This function moves a plant in the Garden
		
		@param oldx the current x value of the plant that should be moved
		@param oldy the current y value of the plant that should be moved
		@param newx the new x coordinate that the plant should be moved to
		@param newy the new y coordinate that the plant should be moved to
		@return true if the plant was moved successfully, false if the plant is not in the garden
	*/			
    boolean movePlant(int oldx, int oldy, int newx, int newy);
    /**
		This function adds a species to the list
		<p>
		It uses default values which can be set by the user using the set methods
		
		@param speciesName the name of the new species
		@return true if the species was added successfully
	*/			    
    boolean addSpecies(String speciesName); 
    /**
		This function removes a species from the speciesList
		
		@param speciesName the name of the species that should be removed
		@return true if the species was removed successfully, false if the species is not in the speciesList
	*/			
    boolean removeSpecies(String speciesName);

	
	/**
		setters for the species fields. The first parameter is the species whose field you would like to set
		and the second parameter is the value you would like to set the field to
	*/
    boolean setDescription(String speciesName, String des);
    boolean setSpeciesType(String speciesName,String speciesType);
    boolean setPlantDate(String speciesName, Date plantDate);
    boolean setPruneDate(String speciesName, Date pruneDate);
    boolean setSunLevel(String speciesName, String sunLevel);
    boolean setColor(String speciesName, int color);
    boolean setSize(String speciesName, int size);

	/**
		getters for the species fields. The parameter is the species whose field you would like to get the value of
	*/
    String getDescription(String speciesName);
    String getSpeciesType(String speciesName); //the species type will be 0 for annual, 1 for perennial, and 2 for tree
    Date getPlantDate(String speciesName);
    Date getPruneDate(String speciesName);
    String getSunLevel(String speciesName);
    int getColor(String speciesName);
    int getSize(String speciesName);
    
    	
            
}
