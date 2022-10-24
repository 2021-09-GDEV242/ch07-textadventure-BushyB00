
/**
 * Holds informtaion about items in diffrent rooms and
 * how much the items weight
 * @author  Evan Skupien 
 * @version 2022.10.23
 */
public class Item
{
    // instance variables 
    private double itemW; 
    private String itemDes; 

    /**
     * Constructor for objects of class Item
     * Gets instance varibales with parameter values 
     */
    public Item()
    {
        // initialise instance variables
        itemDes = "";
        itemW = 0.0; 
    }

    /**
     * Constructor for objects of class Item, 
     * sets the instance variables with parameteres. 
     */
    public Item(String description, double weight)
    {
        // put your code here
        itemDes = description; 
        itemW = weight;
    }
    
    /**
     * Reutrns the description of the item witin a room.
     * @return describes the item and its weight
     */
    public String itemDescritption()
    {
        String itemString = "Item Description: ";
        itemString += this.itemDes +"\n";
        itemString +="Items Weight: "+this.itemW; 
        return itemString; 
    }
}
