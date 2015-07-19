package com.cs1530_group1.gardenapp;

import android.test.suitebuilder.annotation.SmallTest;
//import android.util.Log;

import junit.framework.TestCase;
//
//import junit.*;
//
//import static org.junit.Assert.*;
//
//
//import org.junit.Test;

import java.util.*;

public class GardenTest extends TestCase{

	@SmallTest
	public void testGetPlantList() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|2|23|43|02/24/1994|04/04/1994|tomato|12|33|01/23/1993|03/03/1993|sunflower";
		Garden g = Garden.stringToGarden(garden);
		
		ArrayList<Plant> plantList = g.getPlantList();
		
		//make sure all values of the first plant were set correctly
		assertEquals(plantList.get(0).x, 23);
		assertEquals(plantList.get(0).y, 43);
		assertEquals(plantList.get(0).s.name, "tomato");
		assertEquals(plantList.get(0).plantDate, "02/24/1994");
		assertEquals(plantList.get(0).pruneDate, "04/04/1994");
		
		//make sure all the values of the second plant were set correctly and that getPlantList returns the correct plants
		assertEquals(plantList.get(1).x, 12);
		assertEquals(plantList.get(1).y, 33);
		assertEquals(plantList.get(1).s.name, "sunflower");
		assertEquals(plantList.get(1).plantDate, "01/23/1993");
		assertEquals(plantList.get(1).pruneDate, "03/03/1993");
	}
	
	@SmallTest
	public void testGetSpeciesNames() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		String names[] = g.getSpeciesNames();
		
		//make sure correct species names are returned
		assertEquals(names[0], "tomato");
		assertEquals(names[1], "sunflower");
	}
	
	@SmallTest
	public void testGetSpeciesInfo() {
		String garden = "3|tomato|a tomato species|Annual|high|-318163969|33|15|sunflower|a sunny flower|Perennial|low|-305001985|12|10|wildflower|a wild flower|Annual|mid|-1511192065|25|14|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		Species s = g.getSpeciesInfo("tomato");
		
		//make sure correct values set for tomato species in speciesList structure
		assertEquals(s.name, "tomato");
		assertEquals(s.des, "a tomato species");
		assertEquals(s.type, "Annual");
		assertEquals(s.sun, "high");
		assertEquals(s.color, -318163969); //red color
		assertEquals(s.size, 33);
		assertEquals(s.matTime, 15);
		
		s = g.getSpeciesInfo("sunflower");
		//make sure correct values set for sunflower species in speciesList structure
		assertEquals(s.name, "sunflower");
		assertEquals(s.des, "a sunny flower");
		assertEquals(s.type, "Perennial");
		assertEquals(s.sun, "low");
		assertEquals(s.color, -305001985); //yellow color
		assertEquals(s.size, 12);
		assertEquals(s.matTime, 10);
		
		s = g.getSpeciesInfo("wildflower");
		//make sure correct values set for wildflower species in speciesList structure
		assertEquals(s.name, "wildflower");
		assertEquals(s.des, "a wild flower");
		assertEquals(s.type, "Annual");
		assertEquals(s.sun, "mid");
		assertEquals(s.color, -1511192065); //green color
		assertEquals(s.size, 25);
		assertEquals(s.matTime, 14);		
		
		
		g.addSpecies("testspecies");
		
		s = g.getSpeciesInfo("testasdfd");
		
		assertNull(s);
		
		//make sure the default values get set when adding a new species
		s = g.getSpeciesInfo("testspecies");
		assertEquals(s.name, "testspecies");
		assertEquals(s.type, null);
		assertEquals(s.des, null);
		assertEquals(s.type, null);
		assertEquals(s.sun, null);
		assertEquals(s.color, 0);
		assertEquals(s.size, 0);
		assertEquals(s.matTime, 0);
	}
	
	@SmallTest
	public void testAddPlant() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		boolean test = g.addPlant(34, 12, "03/03/1993", "03/18/1990", "sunflower");
		
		ArrayList<Plant> testList = g.getPlantList();
		
		assertTrue(test); //confirm the plant was added
		//make sure it was added to the plantList structure
		assertEquals(testList.get(1).x, 34);
		assertEquals(testList.get(1).y, 12);
		assertEquals(testList.get(1).s.name, "sunflower");
		assertEquals(testList.get(1).plantDate, "03/03/1993");
		assertEquals(testList.get(1).pruneDate, "03/18/1990");
	}
	
	@SmallTest
	public void testRemovePlant() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		//remove a plant that does exist
		boolean test = g.removePlant(23, 43);
		
		assertTrue(test);
		
		//try to remove a plant that doesnt exist
		test = g.removePlant(1, 1);
		
		assertFalse(test);
	}	
	
	@SmallTest
	public void testMovePlant() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		boolean test = g.movePlant(23, 43, 24, 45);
		ArrayList<Plant> plantList = g.getPlantList();
		
		//make sure the right plant is moved to the right position
		assertEquals(plantList.get(0).x, 24);
		assertEquals(plantList.get(0).y, 45);
	}
	
	@SmallTest
	public void testAddSpecies() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		boolean test = g.addSpecies("blueflower");
		
		assertTrue(test);
		
		String[] speciesName = g.getSpeciesNames();
		
		//confirm that the species is in the speciesList
		assertEquals(speciesName[2], "blueflower");
	}	
	
	@SmallTest
	public void testRemoveSpecies() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		
		//remove species that is actually in the list
		boolean test = g.removeSpecies("tomato");
		
		assertTrue(test);
		
		//try to remove species that is not in the list
		
		test = g.removeSpecies("fakespecies");
		
		assertFalse(test);
	}
	
	
	@SmallTest
	public void testSets() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
		
		boolean test1 = g.addSpecies("blueflower");
		boolean test2 = g.setColor("blueflower", 25);
		boolean test3 = g.setDescription("blueflower", "A blue flower");
		boolean test4 = g.setSize("blueflower", 20);
		boolean test5 = g.setSpeciesType("blueflower", "Tree");
		boolean test6 = g.setSunLevel("blueflower", "high");
		boolean test7 = g.setPruneDate(23, 43, "03/01/1993");
		boolean test8 = g.setPruneDate(23, 43, "12/12/1912");
		
		boolean test9 = g.setColor("noflower", 25);
		boolean test10 = g.setDescription("noflower", "A blue flower");
		boolean test11 = g.setSize("noflower", 20);
		boolean test12 = g.setSpeciesType("noflower", "Tree");
		boolean test13 = g.setSunLevel("noflower", "high");
		
		boolean test14 = g.setMatTime("blueflower", 100);
		boolean test15 = g.setMatTime("noflower", 100);
		
		//make sure that sets were successful because blueflower does exist
		assertTrue(test1);
		assertTrue(test2);
		assertTrue(test3);
		assertTrue(test4);
		assertTrue(test5);
		assertTrue(test6);
		assertTrue(test7);
		assertTrue(test8);
		assertTrue(test14);
		
		//make sure that sets were unsucessful because noflower does not exist in the speciesList
		assertFalse(test9);
		assertFalse(test10);
		assertFalse(test11);
		assertFalse(test12);
		assertFalse(test13);
		assertFalse(test15);
	}	
	
	@SmallTest
	public void testGets() {
		String garden = "2|tomato|a tomato species|Annual|high|25|33|15|sunflower|a sunny flower|Perennial|low|32|12|10|1|23|43|01/23/1993|03/03/1993|tomato";
		Garden g = Garden.stringToGarden(garden);
				
		String type = g.getSpeciesType("tomato");
		String sun = g.getSunLevel("tomato");
		String des = g.getDescription("tomato");
		String pruneDate = g.getPruneDate(23, 43);
		String plantDate = g.getPlantDate(23,43);
		int color = g.getColor("tomato");
		int size = g.getSize("tomato");
		int matTime = g.getMatTime("tomato");
		
		// make sure correct values are set for the species
		assertEquals(type,"Annual");
		assertEquals(sun, "high");
		assertEquals(des, "a tomato species");
		assertEquals(color, 25);
		assertEquals(size, 33);
		assertEquals(matTime, 15);
		assertEquals(pruneDate,  "03/03/1993");
		assertEquals(plantDate, "01/23/1993"); //this test passes most of the time, but sometimes the milliseconds dont line up
		
		g.addSpecies("newSpecies");
		
		//make sure correct default values are set
		type = g.getSpeciesType("newSpecies");
		sun = g.getSunLevel("newSpecies");
		des = g.getDescription("newSpecies");
		color = g.getColor("newSpecies");
		size = g.getSize("newSpecies");
		matTime = g.getMatTime("newSpecies");
		
		assertEquals(type, null);
		assertEquals(sun, null);
		assertEquals(des, null);
		assertEquals(color, 0);
		assertEquals(size, 0);
		assertEquals(matTime, 0);
		
		//make sure that correct value returned when species does not exist
		
		type = g.getSpeciesType("fakeSpecies");
		sun = g.getSunLevel("fakeSpecies");
		des = g.getDescription("fakeSpecies");
		color = g.getColor("fakeSpecies");
		size = g.getSize("fakeSpecies");	
		matTime = g.getMatTime("fakeSpecies");
		
		assertEquals(type, null);
		assertEquals(sun, null);
		assertEquals(des, null);
		assertEquals(color, 0);
		assertEquals(size, 0);
		assertEquals(matTime, 0);
		
	}

	/*
		creates a new empty garden
	 */
	@SmallTest
	public void testCreateGarden(){
		Garden garden = new Garden();
		garden.addSpecies("Mint");
		garden.setDescription("Mint", "it's a plant");
		garden.setSize("Mint", 5);
		garden.setColor("Mint", 0xFFFFFF);
		garden.setSunLevel("Mint", "half");

		String[] test = {"Mint"};

		assertEquals(garden.getSpeciesNames()[0],test[0]);

		assertEquals("it's a plant", garden.getDescription("Mint"));

		assertEquals(5, garden.getSize("Mint"));

		assertEquals(garden.getColor("Mint"), 0xFFFFFF);

		assertEquals(garden.getSunLevel("Mint"), "half");
	}

	/*
		starts with an empty string garden
	 */
	@SmallTest
	public void testEmptyStringCreate(){
		Garden garden = Garden.stringToGarden("");
		garden.addSpecies("Mint");

		String[] test ={"Mint"};

		assertEquals(garden.getSpeciesNames()[0],test[0]);
	}

	/*
		starts with a null input
	 */
	@SmallTest
	public void testNullStringCreate(){
		Garden garden = Garden.stringToGarden(null);
		garden.addSpecies("Mint");

		String[] test ={"Mint"};

		assertEquals(garden.getSpeciesNames()[0],test[0]);
	}
	
	

}
