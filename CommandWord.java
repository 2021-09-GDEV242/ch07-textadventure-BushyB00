/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Evan Skupien 
 * @version 2022.10.23
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), EAT("eat"), 
    BACK("back"), TAKE("take"), DROP("drop"), ITEM("item"), EATCOOKIE("eatcookie"),
    CHARGE("charge"), FIRE("fire");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
