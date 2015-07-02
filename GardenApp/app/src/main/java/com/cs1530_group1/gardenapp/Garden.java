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
        
    /* Sprint 3 Methods */
    
    //done
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
    
    //done
    public ArrayList<Plant> getPlantList()
    {
    	ArrayList<Plant> newList = new ArrayList<Plant>(plantList);
        return newList;
    }
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
    public boolean addSpecies(String speciesName)
    {
        Species newSpecies = new Species(speciesName, null, null, null, null, null, 0, 0);
        speciesList.add(newSpecies);
        
        return true;
    }
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
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
    
    //done
    public String getDescription(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.des;
    	}
    	
        return null;
    }
    
    //done
    public String getSpeciesType(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.type;
    	}
    	
        return null;
    }
    
    //done
    public Date getPlantDate(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.plantDate;
    	}
    	
        return null;
    }
    
    //done
    public Date getPruneDate(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.pruneDate;
    	}
    	
        return null;
    }
    
    //done
    public String getSunLevel(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.sun;
    	}
    	
        return null;
    }
    
    //done
    public int getColor(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.color;
    	}
    	
        return 0;
    }
    
    //done
    public int getSize(String speciesName)
    {
    	Species s = getSpecies(speciesName);
    	
    	if (s != null)
    	{
        	return s.size;
    	}
    	
        return 0;
    }
    
    /* Sprint 2 methods */
    
    
	/**
	 * Allows the garden to be serialized into a string
	 * <p>
	 * The format of the string returned is as follows:
	 * #species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
	 *
	 * @return String representing garden's current string
	 */
    
//    public String GardenToString()
//    {
//        String garden = null;
//        
//        if (speciesList.size() > 0)
//        {
//            int numSpecies = speciesList.size();
//            String type = null, sun = null;
//            String prune, water, fertilize;
//            garden = String.valueOf(numSpecies) + ":";
//            
//            for (Species s : speciesList)
//            {
//                type = s.type;
//                sun = s.sun;
//                prune = String.valueOf(s.prune);
//                water = String.valueOf(s.water);
//                fertilize = String.valueOf(s.fertilize);
//                garden += type + ":" + sun + ":" + prune + ":" + water + ":" + fertilize + ":";
//            }
//            
//            int numPlants = plantList.size();
//            String x, y;
//            garden += String.valueOf(numPlants);
//            
//            for (Plant p : plantList)
//            {
//                x = String.valueOf(p.x);
//                y = String.valueOf(p.y);
//                type = p.s.type;
//                
//                garden += ":" + x + ":" + y + ":" + type;
//            }
//            
//        }
//        
//        return garden;
//    }
//    
//	/**
//	 * Allows a garden to be initialized from a String
//	 * <p>
//	 * The format of the string entered as a parameter is as follows:
//	 * #species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
//	 * 
//	 * @param garden the string representation of the garden
//	 * @return true if garden initialized without error
//	 */	
//    public boolean StringToGarden(String garden)
//    {
//        String parsedGarden[] = garden.split(":");
//        
//        int numSpecies = Integer.parseInt(parsedGarden[0]);
//        speciesList = new ArrayList(numSpecies);
//        String sun = null, type = null;
//        int water, fertilize, prune;
//        int i = 0;
//        int x, y;
//        
//        //multiply by 5 and do i+= 5 because there are five items per species
//        for (i = 1; i < (numSpecies * 5); i+=5)
//        {
//            type = parsedGarden[i];
//            sun = parsedGarden[i+1];
//            prune = Integer.parseInt(parsedGarden[i+2]);
//            water = Integer.parseInt(parsedGarden[i+3]);
//            fertilize = Integer.parseInt(parsedGarden[i+4]);
//            
//            Species newSpecies = new Species(type, sun, prune, water, fertilize);
//            speciesList.add(newSpecies);
//        }
//        
//        
//        int numPlants = Integer.parseInt(parsedGarden[i]);
//        
//        i++;
//        
//        plantList = new ArrayList(numPlants);
//        
//        int j = 0;
//        int counter = numPlants * 3 + i;
//        
//        for (i = i; i < counter; i+= 3)
//        {
//            x = Integer.parseInt(parsedGarden[i]);
//            y = Integer.parseInt(parsedGarden[i+1]);
//            type = parsedGarden[i+2];
//            j = 0;
//            
//            while (true)
//            {
//                if (speciesList.get(j).type.equalsIgnoreCase(type))
//                {
//                    Plant newPlant = new Plant(x, y, speciesList.get(j));
//                    plantList.add(newPlant);
//                    break;
//                }
//                
//                j++;                
//            }                       
//        }
//        
//        return true;
//    }    
//	
//	/**
//	 * Returns a list of current species
//	 * <p>
//	 * 
//	 * @return String array of species
//	 */		
//    public String[] ListSpecies()
//    {
//        String[] speciesNames = null;
//        
//        if (speciesList == null)
//        {
//            return null;
//        }
//        else
//        {
//            speciesNames = new String[speciesList.size()];
//        }
//        
//        for (int i = 0; i < speciesList.size(); i++)
//        {
//            speciesNames[i] = speciesList.get(i).type;
//        }
//        
//        return speciesNames;
//    }
//	
//	/**
//	 * Returns detailed info of the species that is passed in as parameter
//	 * <p>
//	 * The format of the string returned is as follows:
//	 * type:sunValue:pruneValue:waterValue:fertilizeValue
//	 * 
//	 * @param species the species that the detailed info is needed for
//	 * @return String of species info
//	 */		
//    public String GetSpeciesInfo(String species)
//    {
//        String info = null;
//       //tomato:sunValue:pruneValue:waterValue:fertilizeValue
//        String type = null, sun = null, prune = null , water = null, fertilize = null;
//        
//        for (Species s: speciesList)
//        {
//            if (s.type.equals(species))
//            {
//                type = s.type;
//                sun = s.sun;
//                prune = String.valueOf(s.prune);
//                water = String.valueOf(s.water);
//                fertilize = String.valueOf(s.fertilize);
//                info = type + ":" + sun + ":" + prune + ":" + water + ":" + fertilize;
//            }
//        }
//        
//        return info;
//    }
//	
//	
//	/**
//	 * Checks if a certain Plant is currently in the garden
//	 * <p>
//	 * 
//	 * @param p the Plant that the user of this class needs to know if it is in the garden
//	 * @return true if Plant p is currently in garden, otherwise false
//	 */	    
//    public boolean HasPlant(Plant p)
//    {        
//        boolean inList = false;
//        
//        for (Plant plnt : plantList)
//        {
//            if (plnt.x == p.x && plnt.y == p.y)
//            {
//                inList = true;
//                break;
//            }
//        }
//        
//        return inList;
//    }
//    
//	/**
//	 * Checks if a certain Plant is currently in the garden
//	 * <p>
//	 * 
//	 * @param x the x coordinate of the Plant that the user of this class needs to know if it is in the garden
//	 * @param y the y coordinate of the Plant that the user of this class needs to know if it is in the garden	 
//	 * @return true if the plant with the specified x y coordinates is currently in garden, otherwise false
//	 */	 	
//    public boolean HasPlant(int x, int y)
//    {
//        boolean inList = false;
//        
//        for(Plant p : plantList)
//        {
//            if (p.x == x && p.y == y)
//            {
//                inList = true;
//                break;
//            }
//        }
//        
//        return inList;
//        
//    }
    
}
