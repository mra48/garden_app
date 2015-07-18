package com.cs1530_group1.gardenapp;



/**
 * The Plant class represents actual plants currently planted in the garden. Each plant consists
 * the attributes of an x and y coordinate for its location and a Species of the plant. These
 * attributes are modified through the Garden class 
 *
 * @author Muneeb
 */
public class Plant {
    
    int x, y; //the x and y coordinates of the plant
    String plantDate; //the plantDate of the plant
    String pruneDate; //the prune date of the plant
    Species s; //the associated species of the plant
	
    /**
		standard constructor to initialize values
		
		@param s the associated Species of the plant
		@param x the x coordinate of the plant
		@param y the y coordinate of the plant
	*/
    public Plant(int x, int y, String plantDate, String pruneDate, Species s)
    {
        this.x = x;
        this.y = y;
        this.plantDate = plantDate;
        this.pruneDate = pruneDate;
        this.s = s;
    }
    
}
