import java.util.*;

/**
 * Player class, it holds the name, current room, 
 * max weight the player can carry, and previous room info.
 * @author  Evan Skupien 
 * @version 2022.10.23
 */
public class Player
{
    // instance variables - replace the example below with your own
    private String playerName; 
    private Room currentRoom; 
    private int maxWeight; 
    private Stack<Room> roomStack;
    private Item holdingItem;
    public String[] itemCarry = new String[20];
    public int i=0;
    public int weight;
    public int maximumWeight;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
       playerName ="";
       currentRoom= null; 
       maxWeight = 10000; 
       roomStack = new Stack<Room>();
       holdingItem = null;
    }
    
     /**
     * parameterized constructor 
     */
    public Player(String name, Room curRoom, int maxW)
    {
        this.playerName = name; 
        this.currentRoom = curRoom; 
        this.maxWeight = maxW; 
        roomStack = new Stack<Room>();
        holdingItem= null;
    }
    
     /**
     * to set and get the players name
     */
    public void setPlayerName(String pN)
    {
        this.playerName = pN;
    }
    
    
    public String getPlayerName()
    {
        return this.playerName;
    }
    
     /**
     * To set and get the current room
     */
    public void setCurRoom(Room curRoom)
    {
        this.currentRoom = curRoom; 
    }
    
     public Room getCurRoom()
    {
        return this.currentRoom;
    }
    
    /**
     * To set and get the the max weight 
     */
     public void setMaxW(int maxW)
    {
        this.maxWeight = maxW; 
    }
    
    public int getMaxW()
    {
        return maxWeight; 
    }
    
    /**
     * To set and get the an item that is being held
     */
    public void setHoldingItem(Item itemPick)
    {
        holdingItem = itemPick;
    }
    
    public Item getHoldingItem()
    {
        return holdingItem;
    }
    
    /**
     * To get the player description
     */
    public String getPlayerDes()
    {
        String result = "Player "+playerName+":\n";
        if (holdingItem != null)
        {
        result += "You are carrying a/an" + holdingItem.getItemName()+"item in hand. \n\n";
        }   
        result += currentRoom.getLongDescription(); 
        return result;
        
    }
    
    /**
     * To exit the current room the player is in
     */
    public Room getPlayerExit(String direc)
    {
        return currentRoom.getExit(direc);
    }
    
    /**
     * To set the players current entrance to a room
     */
    public void setPlayerEntrance(Room nextRoom)
    {
        roomStack.push(currentRoom);
        currentRoom = nextRoom; 
    }
    
    /**
     * To move a player back a room
     */
    public void movePlayerBack()
    {
        if(roomStack.empty())
        {
            System.out.println("Sorry you are outside the Uni and there is no place to go");
        }
        else 
        {
            currentRoom = roomStack.pop(); 
            System.out.println("player "+playerName+":");
            if(holdingItem != null)
            {
            System.out.println("You are carrying"
            + " a/an "+ holdingItem.getItemName() + " item in hand. \n\n");
            }

            System.out.println(currentRoom.getLongDescription()); 
        }
    
        }
        
        /**
     * check to see if the item can be picked up by the players 
     * @return boolean 
     */
         public boolean canBePickedup (String itemName)
        {
            Item item = currentRoom.getItem(itemName);
             if (item == null)
             {
                 return false;
             }
             if (item.getW() < maxWeight &&!alreadyItemExistsInHand())
             {
                 return true;
             }
             else 
             {
                 return false;
             }
    }
    
    /**
     * Checks whether an item can be dropped 
     * @return boolean 
     */
    public boolean canBeDropped (String itemName)
    {
        if (holdingItem.getItemName().equalsIgnoreCase(itemName) && alreadyItemExistsInHand())
        {
            return true;
        }
        else
        {
        return false;
        }
    }
    
    /**
     * To set and get the current room
     */
    public boolean alreadyItemExistsInHand()
    {
        if (holdingItem != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
        
    }
    
    /**
     * To pick up an item in a room 
     */
    public void pickupItem(String itemName)
    {
      if((maxWeight + weight) < 2000)
      {
        this.itemCarry[i] = itemName; System.out.println("Item Carried : " + itemCarry[i]); i++; maximumWeight += weight;
      }
        else
        {
            System.out.println("You can not carry this item ");
        }
            return;
        
    }
    
    /**
     * To drop an item that was picked up in a room 
     */
    public void dropItem(String itemName)
    {
        if(i <= 0)
        {
            System.out.println("No more items to drop");
        }
            else
            {
            --i; System.out.println("Item Dropped : "+itemCarry[i]); 
            this.itemCarry[i] = null;
            }   
        
    }
    
    /**
     * Prints the current item that you are holding with weight
     */
    public void printItem()
    {
        System.out.println("You contains following item\n");
        for(int j = 0; j <i; j++)
        {
        System.out.println(" " + (+1) + " " + itemCarry[j]);
        }   
        System.out.println("\nTotal Weight : " +maximumWeight);
    }
    
    /**
     * To eat a cookie and increase the amount you can carry
     */
    public void eatCookie()
    {
        if((maxWeight + weight) < 2000)
        {
        System.out.println("Eats Magic Cookie"); maxWeight += 5;
        }   
            else
            {
            System.out.println("You can not eat magic cookie");
            }
    
    }   
}
