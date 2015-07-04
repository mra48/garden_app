package com.cs1530_group1.gardenapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Muneeb
 */
public class Plant {
    
    int x, y; //the x and y coordinates of the plant
    Species s; //the associated species of the plant
	
    /**
		standard constructor to initialize values
	*/
    public Plant(int x, int y, Species s)
    {
        this.x = x;
        this.y = y;
        this.s = s;
    }
    
}
