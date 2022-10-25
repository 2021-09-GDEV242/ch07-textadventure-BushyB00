import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Evan Skupien 
 * @version 2022.10.23
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;  
    private ArrayList<Item> roomItem; // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    
    public ArrayList<Item> getRoomItems()
    {
        return roomItem; 
    }
    
    /**
     * 
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        roomItem = new ArrayList<Item>(); 
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getItemsInRoom() + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
     /**
     * Return a string of items in the room
     */
    public String getItemsInRoom()
    {
        String returnItems = "Items in the room are: \n"; 
        for(Item item : roomItem) 
        {
            returnItems+= item.itemDescritption()+"\n";
        }
        return returnItems;
    }
    
    /**
     * Adds an item to the room 
     */
    public void addItem(Item item) 
    {
        roomItem.add(item);
    }
    
    /**
     * To get an item that is in a room if it is in the room
     */
    public Item getItem(String itemName)
    {
        for (int i = 0; i < roomItem.size(); i++)
        {
            if (roomItem.get(i).getItemName().equalsIgnoreCase (itemName))
            {
                return roomItem.get(i);
            }
        }
        return null;
    }
    
    /**
     * To remove an item that the player has picked up 
     */
    public void removeItem (Item item)
    {
        for (int i = 0; i < roomItem.size(); i++)
        {
            if (roomItem.get(i) == item)
            {
                roomItem.remove (item); break;
            }
        }
    }
    
}

