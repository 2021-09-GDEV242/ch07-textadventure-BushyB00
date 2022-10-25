
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
    private String name; 

    /**
     * Constructor for objects of class Item
     * Gets instance varibales with parameter values 
     */
    public Item()
    {
        // initialise instance variables
        name= "";
        itemDes = "";
        itemW = 0.0; 
    }

    /**
     * Constructor for objects of class Item, 
     * sets the instance variables with parameteres. 
     */
    public Item(String name, String description, double weight)
    {
        // put your code here
        this.name = name;
        itemDes = description; 
        itemW = weight;
    }
    
    /**
     * Reutrns the description of the item witin a room.
     * @return describes the item and its weight
     */
    public String itemDescritption()
    {
        String itemString = "Name: " + this.name;
        itemString +="Item Description: ";
        itemString += this.itemDes +"\n";
        itemString +="Items Weight: "+this.itemW; 
        return itemString; 
    }
    //Gets the items name 
    public String getItemName()
    {
        return name; 
    }
    
    // retunrs the getter method to the item description 
     public String getDesOfItem()
     {
         return itemDes;
     }
     // add tHe getter method to the items weight
     public double getW()
     {
         return itemW;
     }
}
