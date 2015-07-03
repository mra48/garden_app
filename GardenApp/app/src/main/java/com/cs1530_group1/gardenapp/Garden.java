package com.cs1530_group1.gardenapp;

import java.text.*;
import java.util.*;

/**
 * @author Muneeb Alvi
 *
 */
public class Garden implements GardenInterface{

    private ArrayList<Species> speciesList;
    private ArrayList<Plant> plantList;

    /**
     * The public default constructor.
     * Creates an empty Garden
     */
    public Garden(){
        speciesList = new ArrayList<>();
        plantList = new ArrayList<>();
    }
        
    
    //format for the string representation of the garden-> #species-speciesName-speciesDescription-speciesType (ex: 0 for annual, 1 for perennial, 2 for tree)-speciesPlantDate(using this format:MM/dd/yyyy HH:mm:ss)-speciesPruneDate(using this format:MM/dd/yyyy HH:mm:ss)-speciesSunLevel-speciesColor-speciesSize-data for next species-#plants-plantx-planty-plantSpecies-data for next plant	
    public static String gardenToString(Garden g)
    {
        String garden = null;
        
        if (g.speciesList.size() > 0)
        {
            int numSpecies = g.speciesList.size();
            String name = null, des = null, sun = null;
            String type = null;
            String plantDate = null, pruneDate = null;
            String color = null, size = null;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");            

            garden = String.valueOf(numSpecies) + "-";
            
            for (Species s : g.speciesList)
            {
                name = s.name;
                des = s.des;
                sun = s.sun;
                type = s.type;
                
                color = String.valueOf(s.color);
                size = String.valueOf(s.size);
                
                plantDate = df.format(s.plantDate);
                pruneDate = df.format(s.pruneDate);

                garden += name + "-" + des + "-" + type + "-" + plantDate + "-" + pruneDate + "-" + sun + "-" + color + "-" + size + "-";
            }
            
            int numPlants = g.plantList.size();
            String x, y;
            garden += String.valueOf(numPlants);
            
            
            for (Plant p : g.plantList)
            {
                x = String.valueOf(p.x);
                y = String.valueOf(p.y);
                name = p.s.name;
                
                garden += "-" + x + "-" + y + "-" + name;
            }
            
        }
        
        return garden;
    }
    
   //format for the string representation of the garden-> #species-speciesName-speciesDescription-speciesType (ex: 0 for annual, 1 for perennial, 2 for tree)-speciesPlantDate(using this format:MM/dd/yyyy HH:mm:ss)-speciesPruneDate(using this format:MM/dd/yyyy HH:mm:ss)-speciesSunLevel-speciesColor-speciesSize-data for next species-#plants-plantx-planty-plantSpecies-data for next plant    
    public static Garden stringToGarden(String g)
    {
        if(g == null)
          return new Garden();
        if (g.isEmpty())
            return new Garden();

      Garden newGarden = new Garden();
      
      String parsedGarden[] = g.split("-");
      
      int i = 0;
      int numSpecies = Integer.parseInt(parsedGarden[0]);
      newGarden.speciesList = new ArrayList<Species>(numSpecies);
      String name = null, des = null, sun = null, type = null;
      int size = 0, color = 0;
      Date plantDate = null, pruneDate = null;
      int x, y;
      
      //multiply by 5 and do i+= 5 because there are five items per species
      for (i = 1; i < (numSpecies * 8); i+=8)
      {
    	  
          name = parsedGarden[i];
          des= parsedGarden[i+1];
          type = parsedGarden[i+2];
          sun = parsedGarden[i + 5];
          color = Integer.parseInt(parsedGarden[i+6]);
          size = Integer.parseInt(parsedGarden[i + 7]);
          
          String parsedDate[] = parsedGarden[i+3].split(" ");
          String parsedDay[] = parsedDate[0].split("/");
          String parsedTime[] = parsedDate[1].split(":");          
          
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.MONTH, Integer.parseInt(parsedDay[0])-1);
          cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parsedDay[1])-1);
          cal.set(Calendar.YEAR, Integer.parseInt(parsedDay[2]));
          cal.set(Calendar.HOUR, Integer.parseInt(parsedTime[0]));
          cal.set(Calendar.MINUTE, Integer.parseInt(parsedTime[1]));
          cal.set(Calendar.SECOND, Integer.parseInt(parsedTime[2]));
          
          plantDate = cal.getTime();
          
          String parsedDate2[] = parsedGarden[i+4].split(" ");
          String parsedDay2[] = parsedDate2[0].split("/");
          String parsedTime2[] = parsedDate2[1].split(":");
          
          
          Calendar cal2 = Calendar.getInstance();
          cal2.set(Calendar.MONTH, Integer.parseInt(parsedDay2[0])-1);
          cal2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parsedDay2[1])-1);
          cal2.set(Calendar.YEAR, Integer.parseInt(parsedDay2[2]));
          cal2.set(Calendar.HOUR, Integer.parseInt(parsedTime2[0]));
          cal2.set(Calendar.MINUTE, Integer.parseInt(parsedTime2[1]));
          cal2.set(Calendar.SECOND, Integer.parseInt(parsedTime2[2]));        
          
          pruneDate = cal2.getTime();
          
                    
          Species newSpecies = new Species(name, des,sun,  type, plantDate, pruneDate, color, size);
          newGarden.speciesList.add(newSpecies);
      }
      
      
      int numPlants = Integer.parseInt(parsedGarden[i]);
      
      i++;
      
      newGarden.plantList = new ArrayList<Plant>(numPlants);
      
      int j = 0;
      int counter = numPlants * 3 + i;
      
      for (i = i; i < counter; i+= 3)
      {
          x = Integer.parseInt(parsedGarden[i]);
          y = Integer.parseInt(parsedGarden[i+1]);
          name = parsedGarden[i+2];
          j = 0;
          
          while (true)
          {
              if (newGarden.speciesList.get(j).name.equalsIgnoreCase(name))
              {
                  Plant newPlant = new Plant(x, y, newGarden.speciesList.get(j));
                  newGarden.plantList.add(newPlant);
                  break;
              }
              
              j++;                
          }                       
      }
      
      return newGarden;
    }
    
    public ArrayList<Plant> getPlantList()
    {
    	ArrayList<Plant> newList = new ArrayList<Plant>(plantList);
        return newList;
    }
    
    public String[] getSpeciesNames()
    {
        int listSize = speciesList.size();
        String speciesNames[] = new String[listSize];
        int i = 0;
        
        for (Species s : speciesList)
        {
            String newString = new String(s.name);
            speciesNames[i] = newString;
            i++;
        }
        
        return speciesNames;
    }
    
    public Species getSpeciesInfo(String speciesName)
    {
    	Species newSpecies = null;
        for (Species s : speciesList)
        {
            if (s.name.equals(speciesName))
            {
            	
            	newSpecies = new Species(s.name, s.des, s.sun, s.type, (Date) s.plantDate.clone(), (Date) s.pruneDate.clone(), s.color, s.size);
                break;
            }
        }
        
        return newSpecies;
    }
    
    public boolean addPlant(int x, int y, String speciesName)
    {
        for(Species s: speciesList)
        {
            if (s.name.equals(speciesName))
            {
                Plant newPlant = new Plant(x, y, s);
                plantList.add(newPlant);
                return true;
            }
        }
                
        return false;
    }
    
    public boolean removePlant(int x, int y)
    {
        for(Plant p: plantList)
        {
            if (p.x == x && p.y == y)
            {
                plantList.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public boolean movePlant(int oldx, int oldy, int newx, int newy)
    {
        for(Plant p: plantList)
        {
            if (p.x == oldx && p.y == oldy)
            {
                p.x = newx;
                p.y = newy;
                
                return true;
            }
        }
        return false;
    }
    
    public boolean addSpecies(String speciesName)
    {
        Species newSpecies = new Species(speciesName, null, null, null, null, null, 0, 0);
        speciesList.add(newSpecies);
        
        return true;
    }
    
    public boolean removeSpecies(String speciesName)
    {
        for(Species s: speciesList)
        {
            if (s.name.equals(speciesName))
            {
                speciesList.remove(s);
                return true;
            }
        }
        return false;
        
    }
    
    private Species getSpecies(String speciesName)
    {
    	
        for(Species s: speciesList)
        {
            if (s.name.equals(speciesName))
            {
                return s;
            }
        }
    	
    	return null;
    }
    
    public boolean setDescription(String speciesName, String des)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.des = des;
        	return true;
    	}

    	
        return false;
    }
    
    public boolean setSpeciesType(String speciesName,String speciesType)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.type = speciesType;
        	return true;
    	}
    	
        return false;
    }
    
    public boolean setPlantDate(String speciesName, Date plantDate)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.plantDate = plantDate;
        	return true;
    	}

    	
        return false;
    }
    
    public boolean setPruneDate(String speciesName, Date pruneDate)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.pruneDate = pruneDate;
        	return true;
    	}

    	
        return false;
    }
    
    public boolean setSunLevel(String speciesName, String sunLevel)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.sun = sunLevel;
        	return true;
    	}

    	
        return false;
    }
    
    public boolean setColor(String speciesName, int color)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.color = color;
        	return true;
    	}

    	
        return false;
    }
    
    public boolean setSize(String speciesName, int size)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	s.size = size;
        	return true;
    	}

    	
        return false;
    }
  
    public String getDescription(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.des;
    	}
    	
        return null;
    }
    
    public String getSpeciesType(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.type;
    	}
    	
        return null;
    }
  
    public Date getPlantDate(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.plantDate;
    	}
    	
        return null;
    }
    
    public Date getPruneDate(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.pruneDate;
    	}
    	
        return null;
    }
    
    public String getSunLevel(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.sun;
    	}
    	
        return null;
    }
    
    public int getColor(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.color;
    	}
    	
        return 0;
    }
    
    public int getSize(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.size;
    	}
    	
        return 0;
    }  
}
