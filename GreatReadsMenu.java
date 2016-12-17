import java.util.ArrayList;
import userCommunication.UserInteraction;
import userCommunication.Menu;

/**
 * this class is to creat a menu for the program
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class GreatReadsMenu
{
    private UserInteraction ui;
    private Menu menu;
    private static final int LOW_YEAR = 1450, HIGH_YEAR = 2100, FIRST_ELEMENT = 1; //the first element in the list

    public GreatReadsMenu()
    {
        if(ui == null) ui = new UserInteraction(); 
        if(menu == null) menu = new Menu(ui);
    }

    /**
     * print welcome
     */
    public void welcome()
    {        
        ui.println(" ============GREAT READS============ ");
        ui.println("|                                   |");
        ui.println("|                                   |");
        ui.println("|           WELCOME USER!           |");
        ui.println("|                                   |");
        ui.println("|                                   |");
        ui.println(" =================================== ");
        ui.println("The system is loading...");
        ui.pause();
    }

    /**
     * print ending
     */
    public void ending(){ui.println("******Thank you*******");}

    /**
     * store menu into the menu list
     */
    public void mainMenu()
    {
        final boolean clearMenu = true;
        menu.addMenuOption("1", "List all books");
        menu.addMenuOption("2", "List all shelves");
        menu.addMenuOption("3", "Detail of a shelf");
        menu.addMenuOption("4", "Detail of a book");
        menu.addMenuOption("5", "Add or remove book");
        menu.addMenuOption("6", "Add or remove shelf");
        menu.addMenuOption("7", "Add review to book");
        menu.addMenuOption("8", "Add or remove book from shelves");
        menu.addMenuOption("9", "Quit the program");
        menu.clearWhenMenuIsPrinted(clearMenu);
    }

    /**
     * store the sub menu of a list of book or shelf with the sign of "1."
     * @param <detail>
     * @param <detialHeader>
     */
    public <T> void detailMenu(ArrayList<T> detail, String detialHeader)
    {
        T tempData = null;
        ui.println("******" + detialHeader + "******");
        for(int i = 0, length = detail.size(); i < length; i++)
        {
            if((tempData = detail.get(i)) instanceof Book)
                menu.addMenuOption((i + 1) + "", ((Book) tempData).getTitle()); //just print their name
            else menu.addMenuOption((i + 1) + "", ((Shelf) tempData).getName());
        }
    }

    /**
     * store sub menu of add or remove something
     * return <message>
     */
    public void subMenu(String message)
    {
        menu.addMenuOption("1", "Add " + message);
        menu.addMenuOption("2", "Remove " + message);
    }

    /**
     * the header for book detail section
     */
    public void headerOfBookList()
    {
        ui.println("--------------------------------------------------------------------------------------------------------------------------");
        ui.println(String.format("%23s%36s%24s%20s%19s", "Title", "Author", "Publish Year", "Number of Shelves",
                "Number of Review"));
        ui.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * print header for book detail section
     * @param <message>
     */
    public void header(String message){ui.print(message);}

    /**
     * print all information of an object
     * @param <obj>
     */
    public void showAllInfoAtSameLine(Object obj){ui.print(obj);}

    /**
     * print all information of an object and return a line
     * @param <obj>
     */
    public void showAllInfo(Object obj){ui.println(obj);}

    /**
     * the header for shelf detail section
     */
    public void headerOfShelfList()
    {
        ui.println("--------------------------------------------------------------------------------------------------------------------------");
        ui.println(String.format("%36s%65s", "Name [Description]", "Number of Books"));
        ui.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * ask and convert user choice, from string to number
     * @return <number of user choice>
     */
    public int userChoice(){return Integer.valueOf(menu.getUserChoice().getOption());}

    /**
     * pring a warnning
     * @param <message>
     */
    public void warnning(String message){ui.print(message + " Please try again!");}

    /**
     * pause and flush the screen
     */
    public void pause()
    {
        ui.println("");
        ui.pause();
    }

    /**
     * ask and creat a new book object if user enter is valid
     * @return <new book object that user created>
     */
    public Book addBookMenu()
    {
        return new Book(stringInputCheck(ui.getInput_String("Please enter the book name: ")),
            stringInputCheck(ui.getInput_String("Please enter the author: ")),
            ui.getInput_IntBetween("Please enter the publish year: ", LOW_YEAR, HIGH_YEAR));
    }

    /**
     * ask user to select content by input number before the content
     * @param <lastElement: the last element in the list, not to let user enter the number after that>
     * @param <message>
     * @return <user input - 1, to be a index in the list>
     */
    public int selectElementMenu(int lastElement, String message)
    {
        return translateUserInput(ui.getInput_IntBetween("\nPlease enter the index of the " + message + ": ",
                FIRST_ELEMENT, lastElement));
    }

    /**
     * ask and check user input, creat new shelf, name cannot be empty, skip description section means no description
     * @return <a new shelf object>
     */
    public Shelf addShelfMenu()
    {
        return new Shelf(stringInputCheck(ui.getInput_String("Please enter the shelf name: ")),
            ui.getInput_String("Please enter the description, hit <enter> to skip: "));
    }

    /**
     * ask and check user input, creat new review, review cannot be empty
     * @return <a new review object>
     */
    public Review addReviewMenu()
    {
        return new Review("\r\n" + stringInputCheck(ui.getInput_String("Please comment: ")));
    }

    /**
     * check if user enter empty content
     * @param <message>
     * @return <message: valid one>
     */
    private String stringInputCheck(String message)
    {
        while(message.isEmpty())
            message = ui.getInput_String("Infomation cannot be empty, please re-enter: ");
        return message;
    }

    /**
     * convert user input to index
     * @return <index number>
     */
    private int translateUserInput(int userInput){return userInput - 1;}

    /**
     * clear the menu list, for letting OperationSystem use it
     */
    public void clear(){menu.clear();}
}