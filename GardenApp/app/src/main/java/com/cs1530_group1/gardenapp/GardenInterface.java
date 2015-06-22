package com.cs1530_group1.gardenapp;

/**
 *
 * @author Muneeb
 */
public interface GardenInterface {
    
    /*format for garden string
        #species:tomato:sunValue:pruneValue:waterValue:fertilizeValue:sunflower:sunValue:pruneValue:waterValue:fertilizeValue:#plants:x:y:tomato:x:y:sunflower
    
        Note: I thought that we simply could not reference the same species object in every flower object taht uses that species because if the species was changed/edited, then the plant would not be referncing the right species object
        For example, if two plants both referenced a tomato, maybe one tomato was set up using the default tomato settings and the other tomato was edited and then planted therefore being two different tomato species and thats why i think we need to specify
        the species values for every plant. Therefore the species list will be used to hold the current species objects that can be planted and the plant list will also have the details of all the species that are planted and not simply references to objects
        in the species list
    */
    
    
    //I use overloaded methods so you guys can let me know which ones you will like to use 
    
    String GardenToString(); //returns garden as serialized string
    boolean StringToGarden(String garden); //used to initizlize or update garden (boolean used by other classes to check if function performed as expected)
    
//    boolean AddPlant(Plant p);
//    boolean AddPlant(int x, int y, Species s);
//    boolean AddPlant(int x, int y, String species); //the species string just needs to be the name of the selected species(ex: tomato)
//    
//    boolean MovePlant(Plant p);
//    boolean MovePlant(int oldX, int oldY, int newX, int newY); //i need the old x and y values because i can use them to identify the plant and update its position 
//    
//    boolean RemovePlant(Plant p);
//    boolean RemovePlant(int x, int y);  //the x and y will allow me to identify which plant to remove
//    
//    boolean AddSpecies(Species s);
//    boolean AddSpecies(String newSpecies); //this string needs to have all the details of the species with the following format tomato:sunValue:pruneValue:waterValue:fertilizeValue    
//    
//    boolean RemoveSpecies(Species s);
//    boolean RemoveSpecies(String species); //just need name of species (ex:tomato)
    
    String[] ListSpecies(); //returns an array of the names of all species or null if no species have been created yet
    String GetSpeciesInfo(String species); //returns a string of a single species info in the format tomato:sunValue:pruneValue:waterValue:fertilizeValue or null if species does not exist

    boolean HasPlant(Plant p); //return true if Plant p exists in the garden
    boolean HasPlant(int x, int y); //returns true if a plant (any plant) is located at x,y 
            
}
