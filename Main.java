import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * This is the main class to start the program 
 * This program provides Great Book System for add or remove book and shelf
 * Move books on and off from shelves and display
 * How this program run: 
 - 1. Main send file to the FileScanner
 - 2. FileScanner read the file and store information into Entity classes
 - 3. Book and Shelf object will hold List of information
 - 4. OperationSystem will accept those objects and do the logic part
 - 5. GreatReadsMenu will present menu for user to do their action
 - 6. After client quit, then write all information into file
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class Main
{
    /**
     * [This is important]Enter the file name without txt!
     * If user input file name(not include .txt) from command line, it uses default file.
     * If default file does not exist, it creat the default file, and plug file into FileScanner
     * @param <args: accept user input file name from command line>
     */
    public static void main(String[] args)
    {
        GreatReadsMenu menu = new GreatReadsMenu(); //initialize menu passing it to OperationSystem
        FileOutputStream fop = null;
        final String FILE_NAME = "greatreads.txt";
        
        if(args.length != 0) //if user enter the file name
        {
            try
            {   
                new FileScanner(new FileInputStream(args[0] + ".txt"), args[0], menu);
            }
            catch(FileNotFoundException e)
            {
                menu.warnning("File cannot be found!");
            }
        }
        else
        {
            try
            {   //if user don't enter the file name, then use default one
                new FileScanner(new FileInputStream(FILE_NAME), FILE_NAME, menu);
            }
            catch(FileNotFoundException e)
            {
                try
                {   //if default file is not in the system, then creat a new one
                    fop = new FileOutputStream(FILE_NAME);
                }
                catch(IOException ioException)
                {
                    throw new RuntimeException("File cannot be created!");
                }
                finally
                {
                    try
                    {
                        if(fop != null)
                        {
                            fop.close();
                            new FileScanner(new FileInputStream(FILE_NAME), FILE_NAME, menu);
                        }
                    }
                    catch(Exception otherException)
                    {
                        menu.warnning("File cannot be created, because fail to close file!");
                    }
                }
            }
        }
    }
}