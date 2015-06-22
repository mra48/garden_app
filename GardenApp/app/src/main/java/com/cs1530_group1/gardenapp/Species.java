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
public class Species {
 
    String type;
    String sun;
    int prune;
    int water;
    int fertilize;
    
    public Species(String type, String sun, int prune, int water, int fertilize)
    {
        this.type = type;
        this.sun = sun;
        this.prune = prune;
        this.water = water;
        this.fertilize = fertilize;
    }
    
    
    
}
