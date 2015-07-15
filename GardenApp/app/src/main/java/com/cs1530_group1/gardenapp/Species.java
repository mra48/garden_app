package com.cs1530_group1.gardenapp;

import java.util.*;

/**
 * The Species class represents species types that can be planted as Plants by the user. Each Species consists
 * of a name such as tomato, a description of the species, the type of sun
 * level the species should have, the type of plant (such as Annual), the plant date, the prune date for
 * this type of species based on the prune date, its color representation on the Garden, and its size (radius) that will
 * help determine where it can be placed.These attributes are modified through the Garden class 
 *
 * @author Muneeb
 */
public class Species {
	 
    String name;
    String des;
    String sun;
    String type;
    Date plantDate;
    String pruneDate;
    int color;
    int size;
    
    /**
		standard constructor to initialize values
		
		@param name the name of the species
		@param des a short description of the species
		@param sun the sun level of the species
		@param type the growth patterns of the species such as annual
		@param plantDate the date the species was planted
		@param pruneDate the recommended prune date of the species
		@param color the color of the species when it is represented in the Garden
		@param size the radius of the species that determines where a Plant with this species can be placed	
		
	*/
    public Species(String name, String des, String sun, String type, Date plantDate, String pruneDate, int color, int size)
    {
        this.name = name;
        this.des = des;
        this.sun = sun;
        this.type = type;
        this.plantDate = plantDate;
        this.pruneDate = pruneDate;
        this.color = color;
        this.size = size;
    }
    
    
    
}
