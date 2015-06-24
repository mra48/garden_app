import java.util.*;


/**
 * @author Muneeb Alvi
 *
 */
public class Garden implements GardenInterface{
   
    private ArrayList<Species> speciesList;
    private ArrayList<Plant> plantList;

	/**
	 * Allows the garden to be serialized into a string
	 * <p>
	 * The format of the string returned is as follows:
	 * #species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
	 *
	 * @return String representing garden's current string
	 */    
    public String GardenToString()
    {
        String garden = null;
        
        if (speciesList.size() > 0)
        {
            int numSpecies = speciesList.size();
            String type = null, sun = null;
            String prune, water, fertilize;
            garden = String.valueOf(numSpecies) + ":";
            
            for (Species s : speciesList)
            {
                type = s.type;
                sun = s.sun;
                prune = String.valueOf(s.prune);
                water = String.valueOf(s.water);
                fertilize = String.valueOf(s.fertilize);
                garden += type + ":" + sun + ":" + prune + ":" + water + ":" + fertilize + ":";
            }
            
            int numPlants = plantList.size();
            String x, y;
            garden += String.valueOf(numPlants);
            
            for (Plant p : plantList)
            {
                x = String.valueOf(p.x);
                y = String.valueOf(p.y);
                type = p.s.type;
                
                garden += ":" + x + ":" + y + ":" + type;
            }
            
        }
        
        return garden;
    }
    
	
	/**
	 * Allows a garden to be initialized from a String
	 * <p>
	 * The format of the string entered as a parameter is as follows:
	 * #species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
	 * 
	 * @param garden the string representation of the garden
	 * @return true if garden initialized without error
	 */		
    public boolean StringToGarden(String garden)
    {
        String parsedGarden[] = garden.split(":");
        
        int numSpecies = Integer.parseInt(parsedGarden[0]);
        speciesList = new ArrayList(numSpecies);
        String sun = null, type = null;
        int water, fertilize, prune;
        int i = 0;
        int x, y;
        
        //multiply by 5 and do i+= 5 because there are five items per species
        for (i = 1; i < (numSpecies * 5); i+=5)
        {
            type = parsedGarden[i];
            sun = parsedGarden[i+1];
            prune = Integer.parseInt(parsedGarden[i+2]);
            water = Integer.parseInt(parsedGarden[i+3]);
            fertilize = Integer.parseInt(parsedGarden[i+4]);
            
            Species newSpecies = new Species(type, sun, prune, water, fertilize);
            speciesList.add(newSpecies);
        }
        
        
        int numPlants = Integer.parseInt(parsedGarden[i]);
        
        i++;
        
        plantList = new ArrayList(numPlants);
        
        int j = 0;
        int counter = numPlants * 3 + i;
        
        for (i = i; i < counter; i+= 3)
        {
            x = Integer.parseInt(parsedGarden[i]);
            y = Integer.parseInt(parsedGarden[i+1]);
            type = parsedGarden[i+2];
            j = 0;
            
            while (true)
            {
                if (speciesList.get(j).type.equalsIgnoreCase(type))
                {
                    Plant newPlant = new Plant(x, y, speciesList.get(j));
                    plantList.add(newPlant);
                    break;
                }
                
                j++;                
            }                       
        }
        
        return true;
    }    
	
	/**
	 * Returns a list of current species
	 * <p>
	 * 
	 * @return String array of species
	 */		    
    public String[] ListSpecies()
    {
        String[] speciesNames = null;
        
        if (speciesList == null)
        {
            return null;
        }
        else
        {
            speciesNames = new String[speciesList.size()];
        }
        
        for (int i = 0; i < speciesList.size(); i++)
        {
            speciesNames[i] = speciesList.get(i).type;
        }
        
        return speciesNames;
    }
    
	/**
	 * Returns detailed info of the species that is passed in as parameter
	 * <p>
	 * The format of the string returned is as follows:
	 * type:sunValue:pruneValue:waterValue:fertilizeValue
	 * 
	 * @param species the species that the detailed info is needed for
	 * @return String of species info
	 */		
    public String GetSpeciesInfo(String species)
    {
        String info = null;
       //tomato:sunValue:pruneValue:waterValue:fertilizeValue
        String type = null, sun = null, prune = null , water = null, fertilize = null;
        
        for (Species s: speciesList)
        {
            if (s.type.equals(species))
            {
                type = s.type;
                sun = s.sun;
                prune = String.valueOf(s.prune);
                water = String.valueOf(s.water);
                fertilize = String.valueOf(s.fertilize);
                info = type + ":" + sun + ":" + prune + ":" + water + ":" + fertilize;
            }
        }
        
        return info;
        
    }
    
	/**
	 * Checks if a certain Plant is currently in the garden
	 * <p>
	 * 
	 * @param p the Plant that the user of this class needs to know if it is in the garden
	 * @return true if Plant p is currently in garden, otherwise false
	 */	 	
    public boolean HasPlant(Plant p)
    {        
        boolean inList = false;
        
        for (Plant plnt : plantList)
        {
            if (plnt.x == p.x && plnt.y == p.y)
            {
                inList = true;
                break;
            }
        }
        
        return inList;
    }
    
	/**
	 * Checks if a certain Plant is currently in the garden
	 * <p>
	 * 
	 * @param x the x coordinate of the Plant that the user of this class needs to know if it is in the garden
	 * @param y the y coordinate of the Plant that the user of this class needs to know if it is in the garden	 
	 * @return true if the plant with the specified x y coordinates is currently in garden, otherwise false
	 */	
    public boolean HasPlant(int x, int y)
    {
        boolean inList = false;
        
        for(Plant p : plantList)
        {
            if (p.x == x && p.y == y)
            {
                inList = true;
                break;
            }
        }
        
        return inList;
        
    }
    
}
