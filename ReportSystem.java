import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * accept every information from the system and write them back to the file
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class ReportSystem
{
    final static String FILE_NAME = "greatreads.txt";
    private FileOutputStream fop;
    private ArrayList<Book> allBooks;
    private ArrayList<Shelf> allShelves;

    public ReportSystem(ArrayList<Book> allBooks, ArrayList<Shelf> allShelves)
    {
        this.allBooks = allBooks;
        this.allShelves = allShelves;
        writingIntoFile();
    }

    /**
     * write everything into file
     */
    private void writingIntoFile()
    {
        try
        {
            fop = new FileOutputStream(FILE_NAME);
            writeAllDataIntoFile();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Fail to creat file!");
        }
        finally
        {
            if(fop != null)
            {
                try
                {
                    fop.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * write each section with "####"
     */
    private void writeAllDataIntoFile() throws IOException
    {
        writeShelfSection(); //shelf
        writeDivider(); //####
        writeBookSection(); //book
        writeDivider(); //####
        writeReviewSection(); //review
    }

    /**
     * write shelf section into file
     */
    private void writeShelfSection() throws IOException
    {
        for(Shelf shelf: allShelves)
        {
            if(!shelf.getDescription().equals("No Description"))
                writeEveryDataAtDifferentLine(shelf.getName() + "|" + shelf.getDescription()); //shelf name|description
            else writeEveryDataAtDifferentLine(shelf.getName() + "|"); //empty if it doesn't have
        }
    }

    /**
     * write book section into file
     */
    private void writeBookSection() throws IOException
    {
        for(Book book: allBooks)
        {
            writeEveryDataAtSameLine(book.getTitle() + "|" + book.getAuthor() + "|" + book.getYear() + "#"); //write books' information
            writeShelfNamesInBookSection(book.getShelvesHasThisBook()); //write shelf name that have this book on
            returnLine();
        }
    }

    /**
     * write shelf that has the book on after writing book information
     * @param <shelfHasThisBookOn>
     */
    private void writeShelfNamesInBookSection(ArrayList<Shelf> shelfHasThisBookOn) throws IOException
    {
        for(int i = 0, length = shelfHasThisBookOn.size(); i < length; i++)
        {
            if(i != length - 1) writeEveryDataAtSameLine(shelfHasThisBookOn.get(i).getName() + "|"); 
            else writeEveryDataAtSameLine(shelfHasThisBookOn.get(i).getName()); //last token is not followed by "|"
        }
    }

    /**
     * write review section into file
     */
    private void writeReviewSection() throws IOException
    {
        for(Book book: allBooks)
        {
            for(Review review: book.getReview())
            {
                writeEveryDataAtSameLine(book.getTitle() + "|" + review.getTime()); //write book name|review
                writeEveryDataAtDifferentLine(review.getComment()); //wirte comment
                writeEveryDataAtDifferentLine("#");
            }
        }
    }

    /**
     * write shelf section into file
     */
    private void writeDivider() throws IOException
    {
        writeEveryDataAtDifferentLine("####");
    }

    /**
     * write everything at same line
     * @param <obj>
     */
    private void writeEveryDataAtSameLine(Object obj) throws IOException
    {
        fop.write(obj.toString().getBytes());
    }

    /**
     * write everything at same line and go to next line
     * @param <obj>
     */
    private void writeEveryDataAtDifferentLine(Object obj) throws IOException
    {
        writeEveryDataAtSameLine(obj);
        returnLine();
    }

    /**
     * return a new line in the file
     */
    private void returnLine() throws IOException
    {
        final String NEW_LINE = "\r\n";
        fop.write(NEW_LINE.getBytes());
    }
}
