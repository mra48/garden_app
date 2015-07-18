package com.cs1530_group1.gardenapp;

import android.annotation.SuppressLint;

import java.text.*;
import java.util.*;

/**
 * @author Muneeb Alvi
 *
 */
public class Garden implements GardenInterface{
	
	//list of species
    private ArrayList<Species> speciesList;
	//list of plants
    private ArrayList<Plant> plantList;

    /**
     * The public default constructor.
     * Creates an empty Garden
     */
    public Garden(){
        speciesList = new ArrayList<>();
        plantList = new ArrayList<>();
    }
        
    /**
		This function converts a garden into a string format which can be parsed by users of this class
		format for the string representation of the garden|> #species|speciesName|speciesDescription|speciesType (ex: 0 for annual, 1 for perennial, 2 for tree)|speciesSunLevel|speciesColor|speciesSize|speciesMatTime|data for next species|#plants|plantx|planty|plantPlantDate(using this format:MM/dd/yyyy)|plantPruneDate(using this format:MM/dd/yyyy)|plantSpeciesName|data for next plant		
		
		@param g The garden that is converted to a string
		@return The string representation of the garden
	*/
    public static String gardenToString(Garden g)
    {
		//contains the string representation of the garden
        String garden = null;
        
		//make sure that there are actually items in the garden before trying to serialize it
        if (g.speciesList.size() > 0)
        {
            int numSpecies = g.speciesList.size();
			
            String name;
            String des;
            String sun;
            String type;
            String color;
            String size;
            String matTime;

            garden = String.valueOf(numSpecies) + "|"; //the | will be used as a delimitter
            
			//create string based on the fields of each species
            for (Species s : g.speciesList)
            {
                name = s.name;
                des = s.des;
                sun = s.sun;
                type = s.type;
                
                color = String.valueOf(s.color); 
                size = String.valueOf(s.size);
                matTime = String.valueOf(s.matTime);

                garden += name + "|" + des + "|" + type + "|" + sun + "|" + color + "|" + size + "|" + matTime + "|";
            }
            
			
            int numPlants = g.plantList.size();
            String x, y;
            String plantDate;
            String pruneDate;
            garden += String.valueOf(numPlants);
            
            //append to string based on fields of each plant
            for (Plant p : g.plantList)
            {
                x = String.valueOf(p.x);
                y = String.valueOf(p.y);
                plantDate = p.plantDate;
                pruneDate = p.pruneDate;
                name = p.s.name;
                
                garden += "|" + x + "|" + y + "|" + plantDate + "|" + pruneDate + "|" + name;
            }
            
        }
        
        return garden; //return garden string (null if nothing in garden)
    }
    
	/**
		This function converts a String into a Garden object
		format for the string representation of the garden-> #species|speciesName|speciesDescription|speciesType (ex: 0 for annual, 1 for perennial, 2 for tree)|speciesSunLevel|speciesColor|speciesSize|speciesMatTime|data for next species|#plants|plantx|planty|plantPlantDate(using this format:MM/dd/yyyy)|plantPruneDate(using this format:MM/dd/yyyy)|plantSpeciesName|data for next plant		
		
		@param g The string that is parsed and converted into a Garden
		@return The Garden object
	*/
    public static Garden stringToGarden(String g)
    {
        if(g == null) //return empty garden if string is empty
          return new Garden();
        if (g.isEmpty()) //return empty if garden is empty
            return new Garden();

        Garden newGarden = new Garden(); //the garden that will be returned
      
      String parsedGarden[] = g.split("\\|"); //split the string on the vertical bar (|) character but use two slashes as escape sequence
      
      int i;
      int numSpecies = Integer.parseInt(parsedGarden[0]);
      newGarden.speciesList = new ArrayList<>(numSpecies);
	  
	  //fields of a species
      String name;
        String des;
        String sun;
        String type;
        int size;
        int color;
        int matTime;
		
		//fields of a plant
        int x;
        int y;
        String plantDate;
        String pruneDate;
        
        //multiply by 7 and do i+= 7 because there are five items per species
      for (i = 1; i < (numSpecies * 7); i+=7)
      {
		//parse the string and create values for species and add them to the species list
    	  
          name = parsedGarden[i];
          des= parsedGarden[i+1];
          type = parsedGarden[i+2];
          sun = parsedGarden[i + 3];
          color = Integer.parseInt(parsedGarden[i+4]);
          size = Integer.parseInt(parsedGarden[i + 5]);    
          matTime = Integer.parseInt(parsedGarden[i+6]);
                    
          Species newSpecies = new Species(name, des,sun,  type, color, size, matTime);  //create species based on parsed string		
          newGarden.speciesList.add(newSpecies); //add new species to species list
      }
      
      
      int numPlants = Integer.parseInt(parsedGarden[i]);
      
      i++; //increment i because we need to go to the next parsed token in the string which contains the start of the first plant's information
      
      newGarden.plantList = new ArrayList<>(numPlants); //create arrayList of plants
      
      int j;
      int counter = numPlants * 5 + i; //multiply by 3 and do i+= 3 because there are 3 fields (x,y, species) per plant
      
      for (; i < counter; i+= 5)
      {
		  //get the x, y, and species name for each plant
          x = Integer.parseInt(parsedGarden[i]);
          y = Integer.parseInt(parsedGarden[i+1]);
          plantDate = parsedGarden[i+2];
          pruneDate = parsedGarden[i+3];
          name = parsedGarden[i+4];
          j = 0;
          
          while (true) //look for correct species in species list and break out when it is found
          {
              if (newGarden.speciesList.get(j).name.equalsIgnoreCase(name)) //the species is found
              {
                  Plant newPlant = new Plant(x, y, plantDate, pruneDate, newGarden.speciesList.get(j)); //create new plant
                  newGarden.plantList.add(newPlant); //add new plant to plant list
                  break;
              }
              
              j++;                
          }                       
      }
      
      return newGarden; //return the new garden
    }
    
    /**
		This function returns a copy of the plantList ArrayList	
		
		@return A copy of the plantList ArrayList
	*/	
    public ArrayList<Plant> getPlantList()
    {
    	return new ArrayList<>(plantList); //return a copy of the plantList

    }
    
    /**
		This function returns an array containing all of the speciesNames currently in the species list	
		
		@return an array containing all of the speciesNames currently in the species list	
	*/		
    public String[] getSpeciesNames()
    {
        int listSize = speciesList.size();
        String speciesNames[] = new String[listSize];

		//add species names to array
        for(int i =0; i<speciesNames.length; i++) {
            speciesNames[i] = speciesList.get(i).name;
        }
        
        return speciesNames;
    }
    
    /**
		This function returns a species object based on its name
		
		@param speciesName the name of the species whose object is desired
		@return a copy of the species object
	*/			
    public Species getSpeciesInfo(String speciesName)
    {
    	Species newSpecies = null;
    	
		//go through speciesList looking for the species with the name equal to the parameter
        for (Species s : speciesList)
        {
            if (s.name.equals(speciesName))
            {
            	newSpecies = new Species(s.name, s.des, s.sun, s.type, s.color, s.size, s.matTime);
            		
                break;
            }
        }
        
        return newSpecies; //return null if no species found, and a copy of the species object if it is found
    }
    
    /**
		This function adds a plant to the Garden
		
		@param speciesName the name of the species that will be planted as a plant
		@param x the desired x location of the plant
		@param y the desired y location of the plant
		@param plantDate the desired plant date of the plant
		@param pruneDate the desired prune date of the plant
		@return true if the plant was added successfully, false if the species does not exist in the list
	*/				
    public boolean addPlant(int x, int y, String plantDate, String pruneDate,  String speciesName)
    {
		//look through species list to make sure that the parameter speciesName is actually in the list of species before adding a plant with that species name
        for(Species s: speciesList)
        {
			//a match was found
            if (s.name.equals(speciesName))
            {
				//create a new plant and add it to the list
                Plant newPlant = new Plant(x, y, plantDate, pruneDate, s);
                plantList.add(newPlant);
                return true; //return true for a successful add
            }
        }
                
        return false; //return false if plant couldnt be added because species was not in list
    }
    
    /**
		This function removes a plant from the garden
		
		@param x the x location of the plant to be removed
		@param y the y location of the plant to be removed
		@return true if the plant was removed successfully, false if the plant is not in the garden
	*/					
    public boolean removePlant(int x, int y)
    {
		//look through plant list to find a plant whose coordinates match those that were passed in
        for(Plant p: plantList)
        {
			//a match was found
            if (p.x == x && p.y == y)
            {
				//remove the found plant from the list
                plantList.remove(p);
                return true; //return true for successful removal
            }
        }
        return false; //return false for unsuccessful removal because a plant with the passed in coordinates was not in the list
    }
    
    /**
		This function moves a plant in the Garden
		
		@param oldx the current x value of the plant that should be moved
		@param oldy the current y value of the plant that should be moved
		@param newx the new x coordinate that the plant should be moved to
		@param newy the new y coordinate that the plant should be moved to
		@return true if the plant was moved successfully, false if the plant is not in the garden
	*/					
    public boolean movePlant(int oldx, int oldy, int newx, int newy)
    {
		//look through plant list to find a plant whose coordinates match oldx and oldy
        for(Plant p: plantList)
        {
			//a match was found
            if (p.x == oldx && p.y == oldy)
            {
				//set the plants coordinates to the new desired coordinates
                p.x = newx;
                p.y = newy;
                
                return true; //return true for successful move of plant
            }
        }
        return false; //return false for unsuccessful move of plant because the plant was not found in the list
    }
    
    /**
		This function adds a species to the list
		<p>
		It uses default values which can be set by the user using the set methods
		
		@param speciesName the name of the new species
		@return true if the species was added successfully
	*/						
    public boolean addSpecies(String speciesName)
    {
		//create a new species with default values and let user set them using set methods
        Species newSpecies = new Species(speciesName, null, null, null, 0, 0, 0);
        speciesList.add(newSpecies); //add new species to list
        
        return true; //return true for successful add of species
    }
    
    /**
		This function removes a species from the speciesList
		
		@param speciesName the name of the species that should be removed
		@return true if the species was removed successfully, false if the species is not in the speciesList
	*/						
    public boolean removeSpecies(String speciesName)
    {
    	boolean found; //used to make sure we dont concurrently change the plantList while traversing it
    	
		//look through species list for a match of speciesName
        for(Species s: speciesList)
        {
			//match was found
            if (s.name.equals(speciesName))
            {
            	while (true)
            	{
            		found = false; //reset everytime through while loop
            		
    				for(Plant p: plantList) //remove any plants that are associated with the species
                    {
                        if (p.s.name.equals(speciesName))
                        {
                            plantList.remove(p);
                            found = true; //found plant in this traversal
                            break;
                        }
                    }    
    				
    				if (!found) //no plant with that speciesName was found in the loop, so break out of while loop
    				{
    					break;
    				}
            	}

				
				//remove that species from the list
                speciesList.remove(s);
                return true; //return true for successful removal
            }
        }
        return false; //return false if species was not found in the list
        
    }
    
	
    /**
		This function returns a species object based on its name
		
		@param speciesName the name of the species whose object is desired
		@return a reference to the species object
	*/				
    private Species getSpecies(String speciesName)
    {
		
		//go through speciesList looking for the species with the name equal to the parameter
        for (Species s : speciesList)
        {
            if (s.name.equals(speciesName))
            {
            	
            	return s; //dont return clone because this is private method used to modify s whereas getSpeciesInfo is a public method used to view a particular species
            }
        }
        
        return null; //return null if no species found, and a reference to the species object if it is found
    }
    
	/**
		This function sets the description value for a species
		
		@param speciesName the name of the species whose description is being set
		@param des the desired description
		@return true if the species description was set, false if the species is not in the speciesList
	*/			
    public boolean setDescription(String speciesName, String des)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	s.des = des; //set the description
        	return true; //return true for successful update
    	}

        return false; //return false if species is not in the current list
    }
    
	/**
		This function sets the type value for a species
		
		@param speciesName the name of the species whose type is being set
		@param speciesType the desired type
		@return true if the species type was set, false if the species is not in the speciesList
	*/				
    public boolean setSpeciesType(String speciesName,String speciesType)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	s.type = speciesType; //set the type
        	return true;
    	}
    	
        return false; //return false if the species wasnt found
    }
    
    
    /**
		This function returns a plant object based on its name
		
		@param x the x coordinate of the plant whose object is desired
		@param y the y coordinate of the plant whose object is desired
		@return a reference of the plant object
	*/				
    private Plant getPlant(int x, int y)
    {
		
		//go through plantList looking for the plant with the same x and y fields as the parameters passed in
        for (Plant p : plantList)
        {
            if (p.x == x && p.y == y)
            {
            	return p; //dont return clone because this is private method
            }
        }
        
        return null; //return null if no plant found
    }    
    
    
	/**
		This function sets the plantDate value for a plant
		
		@param plantDate the desired plantDate for the plant
		@param x the x coordinate of the plant whose plantDate should be modified
		@param y the y coordinate of the plant whose plantDate should be modified
		@return true if the plant plantDate was set, false if the plant is not in the plantList
	*/					
    public boolean setPlantDate(int x, int y, String plantDate)
    {
		//get the plant and make sure it exists in the list
    	Plant p = getPlant(x, y);
    	
		//if s exists
    	if (p != null)
    	{
        	p.plantDate = plantDate; //set the plant date
        	return true;
    	}

    	
        return false; //return false if the plant wasnt found
    }
    
	/**
	This function sets the pruneDate value for a plant
	
	@param plantDate the desired pruneDate for the plant
	@param x the x coordinate of the plant whose pruneDate should be modified
	@param y the y coordinate of the plant whose pruneDate should be modified
	@return true if the plant pruneDate was set, false if the plant is not in the plantList
*/						
    public boolean setPruneDate(int x, int y, String pruneDate)
    {
		//get the plant and make sure it exists in the list
    	Plant p = getPlant(x, y);
    	
		//if s exists
    	if (p != null)
    	{
        	p.pruneDate = pruneDate; //set the prune date
        	return true;
    	}

    	
        return false; //return false if the plant wasnt found
    }
    
	/**
		This function sets the sunLevel value for a species
		
		@param speciesName the name of the species whose sunLevel is being set
		@param sunLevel the desired sunLevel
		@return true if the species sunLevel was set, false if the species is not in the speciesList
	*/					
    public boolean setSunLevel(String speciesName, String sunLevel)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	s.sun = sunLevel; //set the sun level
        	return true;
    	}

    	
        return false; //return false if the species wasnt found
    }
    
	/**
		This function sets the color value for a species
		
		@param speciesName the name of the species whose color is being set
		@param color the desired color
		@return true if the species color was set, false if the species is not in the speciesList
	*/					
    public boolean setColor(String speciesName, int color)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	s.color = color; //set the color
        	return true;
    	}

    	
        return false; //return false if the species wasnt found
    }
    
	/**
		This function sets the size value for a species
		
		@param speciesName the name of the species whose size is being set
		@param size the desired size
		@return true if the species size was set, false if the species is not in the speciesList
	*/					
    public boolean setSize(String speciesName, int size)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	s.size = size; //set the size
        	return true;
    	}

    	
        return false; //return false if the species wasnt found
    }
    
	/**
	This function sets the matTime(maturation time) value for a species
	
	@param speciesName the name of the species whose size is being set
	@param matTime the desired matTime
	@return true if the species matTime was set, false if the species is not in the speciesList
*/					
	public boolean setMatTime(String speciesName, int matTime)
	{
		//get the species and make sure it exists in the list
		Species s = getSpecies(speciesName);
		
		//if s exists
		if (s != null)
		{
	    	s.matTime = matTime; //set the size
	    	return true;
		}
	
		
	    return false; //return false if the species wasnt found
	}
    
  
    public String getDescription(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.des; //return the description
    	}
    	
        return null; //return null if the species wasnt found
    }
    
    public String getSpeciesType(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.type; //return the type
    	}
    	
        return null; //return null if the species wasnt found
    }
  
  	/**
		This function gets the plantDate value for a plant
		
		@param x the x coordinate of the plant whose plantDate is desired
		@param y the y coordinate of the plant whose plantDate is desired
		@return the plantDate
	*/				
    public String getPlantDate(int x, int y)
    {
		//get the plant and make sure it exists in the list
    	Plant p = getPlant(x, y);
    	
		//if p exists
    	if (p != null)
    	{
            	return p.plantDate;  			
    	}
    	
        return null; //return null if the plant wasnt found
    }
    
  	/**
		This function gets the pruneDate value for a plant
		
		@param x the x coordinate of the plant whose pruneDate is desired
		@param y the y coordinate of the plant whose pruneDate is desired
		@return the pruneDate
	*/				
    public String getPruneDate(int x, int y)
    {
		//get the plant and make sure it exists in the list
    	Plant p = getPlant(x, y);
    	
		//if p exists
    	if (p != null)
    	{
            	return p.pruneDate;  			
    	}
    	
        return null; //return null if the plant wasnt found
    }
    
  	/**
		This function gets the sunLevel value for a species
		
		@param speciesName the name of the species whose sunLevel is desired
		@return the sunLevel of the species
	*/					
    public String getSunLevel(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.sun; //return the sun level
    	}
    	
        return null; //return null if the species wasnt found
    }
    
  	/**
		This function gets the color value for a species
		
		@param speciesName the name of the species whose color is desired
		@return the color of the species
	*/			
    public int getColor(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.color; //return the color
    	}
    	
        return 0; //return 0 if the species wasnt found
    }
    
  	/**
		This function gets the size value for a species
		
		@param speciesName the name of the species whose size is desired
		@return the size of the species
	*/			
    public int getSize(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.size; //return the size
    	}
    	
        return 0; //return 0 if the species wasnt found
    }  
    
  	/**
		This function gets the matTime value for a species
		
		@param speciesName the name of the species whose matTime is desired
		@return the matTime of the species
	*/			
    public int getMatTime(String speciesName)
    {
		//get the species and make sure it exists in the list
    	Species s = getSpecies(speciesName);
    	
		//if s exists
    	if (s != null)
    	{
        	return s.matTime; //return the matTime
    	}
    	
        return 0; //return 0 if the species wasnt found
    }  
    
}
