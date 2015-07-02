package com.cs1530_group1.gardenapp;

import java.util.*;
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
 
    String name;
    String des;
    String sun;
    String type;
    Date plantDate;
    Date pruneDate;
    int color;
    int size;
    
    public Species(String name, String des, String sun, String type, Date plantDate, Date pruneDate, int color, int size)
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
