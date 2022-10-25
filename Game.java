import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Evan Skupien 
 * @version 2022.10.23
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room roomStack[];
    private int top;
    private Scanner reader; 
    private Player player; 
    private int counter = 0;
    private int randomRoom; 
    private String directions[] = {"north","south","east","west"};
    ArrayList<Room> array_Rooms = new ArrayList<Room>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomStack = new Room[500];
        top=-1;
        player = new Player();
        reader = new Scanner(System.in); 
    }
    
    /**
     * Create the player and gives them a name.
     */
    public void createPlayer()
    {
        System.out.println("Enter your players name: ");
        String name = reader.nextLine(); 
        player.setPlayerName(name);
        createRooms(); 
        System.out.println("Enter max weight the player can carry: ");
        int w = reader.nextInt(); 
        player.setMaxW(w); 
    }

    /**
     * Create all the rooms and link their exits together. Adds transporter so player can be transported to diffrent rooms randomly. 
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, transporter;
        
        // create the rooms
        Item oItem[] = { new Item("Nothing","It is a open area", 0), new Item("Plants", "The open area contains" + " plants", 200), new Item ("Benches", "The open area" + "contains benches to"+ " sit", 1500)}; 
        Item tItem[] = { new Item("Projector","Is used to project the " + "content in visual form", 2000), new Item("Chairs", "To sit in the theater", 5000), new Item("Table","To place items", 200) }; 
        Item pItem[] = { new Item("Soda","Beverages to drink", 300), new Item ("Juxbox", "To get relaxed by listinning"+ " to the music", 500) };
        Item lItem[] = { new Item("Laptops","To work on the systems.",1200), new Item("CD/DVD", "To store information",200) }; 
        Item ofItem[] = { new Item("Telephone","To communicate", 100), new Item("Pens", "To write the information",200), new Item("Magazines", "School Magazines", 500) };
        
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter = new Room("in the transporter room");
        
        array_Rooms.add(outside);
        array_Rooms.add(theater);
        array_Rooms.add(pub);
        array_Rooms.add(lab);
        array_Rooms.add(office);
        
        outside = addItemsToRoom(outside, oItem);
        theater = addItemsToRoom(theater, tItem);
        pub = addItemsToRoom(pub, pItem);
        lab = addItemsToRoom(lab, lItem);
        office = addItemsToRoom(office, ofItem);
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", transporter);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("north", transporter);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        randomRoom = (int) (Math.random() * array_Rooms.size()); 
        
        transporter.setExit ("north", array_Rooms.get (randomRoom)); 
        array_Rooms.remove (randomRoom);
        
        randomRoom = (int) (Math.random() * array_Rooms.size()); 
        
        transporter.setExit ("south",array_Rooms.get (randomRoom)); 
        array_Rooms.remove (randomRoom);
        
        randomRoom = (int) (Math.random() * array_Rooms.size()); 
        
        transporter.setExit ("east",array_Rooms.get (randomRoom)); 
        array_Rooms.remove (randomRoom);
        
        randomRoom = (int) (Math.random() * array_Rooms.size()); 
        
        transporter.setExit("west",array_Rooms.get (randomRoom)); 
        array_Rooms.remove (randomRoom);

        player.setCurRoom(outside);  // start game outside
        
    }

    /**
     *  Main play routine.  Untill counter runs out of time.
     */
    public void play() 
    {        
        createPlayer(); 
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            if(counter>10)
            {
                System.out.println("Sorry your time is up");
            }
            else
            {
            Command command = parser.getCommand();
            finished = processCommand(command);
            counter++;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        createPlayer();
        System.out.println("Hi player"+ player.getPlayerName());
        System.out.println(player.getPlayerDes());
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                lookAround();
                break;
                
            case EAT:
                eatSomething(); 
                break; 
            
            case BACK:
                backRoom(); 
                break;
            
            case TAKE:
                pickupItemFromRoom(command);
                break;
                
            case DROP:
                dropItemInHand(command); 
                break;
                
            case ITEM:
                player.printItem();
                break;
            
            case EATCOOKIE:
                player.eatCookie();
                break;
            
                
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if (nextRoom.getShortDescription().contains("transporter room"))
            {
                System.out.println("You are " + " in the Transporter Room"); 
                System.out.print("So, you are being" +" transported"); 
                direction = directions[(int)(Math.random() * directions.length)]; 
                nextRoom = nextRoom.getExit (direction);
            }
                if (nextRoom == null)
                {
                System.out.println("There is no " +"door!");
                }   
                else
                {
                currentRoom = nextRoom; System.out.println(
                currentRoom.getLongDescription());
                }
            player.setPlayerEntrance(nextRoom);
            System.out.println(player.getPlayerDes());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
   /**
     * prints the room decrption
     */
    private void lookAround()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Exectues the eat command 
     */
    private void eatSomething()
    {
        System.out.println("You have eaten and not hungry anymore.");
    }
    
    /** 
     * Adds the array of items to a room and returns the room. 
     */
    private Room addItemsToRoom (Room room, Item items[])
    {
        for(int i = 0; i < items.length; i++)
        {
            room.addItem(items[i]);
        }
        return room; 
    }
    
     /** 
     * Goes back to the previous room with description
     */
    private void backRoom()
    {
        player.movePlayerBack();
    }
    
     /** 
     * Adds room to room stack
     */
    private void push(Room room)
    {
        if(top == roomStack.length-1)
        {
            System.out.println("Room stack is full"); 
        }
        else 
        {
            roomStack[++top] = room; 
        }
    }
    
    /** 
    * Removes rooms that are on top of the stack
    */
    private Room pop()
    {
        if(top<0){
            System.out.println("Sorry you are outside there are no rooms to go back too!");
            return null;
        }
        else 
        {
            return roomStack[top--];
        }
    }
    
     /**
     * executes the pick up item command and saves its name
     */
    private void pickupItemFromRoom(Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("There is not item"+ " specified...");
            return;
        }
        String itemName = command.getSecondWord();
        player.pickupItem(itemName);
    }
    
     /**
     * Executes the drop item and stores it in itemName
     */
    private void dropItemInHand(Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("There is not item"+ "specified..."); 
            return;
        }
        String itemName = command.getSecondWord();
        player.dropItem (itemName);
    }
   
    
  
    
}
