import java.util.*;

public class Garden implements GardenInterface{
   
    private ArrayList<Species> speciesList;
    private ArrayList<Plant> plantList;
    
    //#species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
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
