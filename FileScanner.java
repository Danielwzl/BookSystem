import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class is for reading the file, creat objects and store them into ArrayLists
 * @author Zilong Wang 
 * @version 1.0 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>                           
 */
public class FileScanner
{
    private ArrayList<Shelf> allShelves;
    private ArrayList<Book> allBooks;
    private Scanner scan;

    public FileScanner(FileInputStream file, String fileName, GreatReadsMenu menu)
    {
        allShelves = new ArrayList<Shelf>();
        allBooks = new ArrayList<Book>();
        scan = new Scanner(file);
        if(scan.hasNext()) importFromFile(); //if file is not empty, then start to read the file
        new OperationSystem(allShelves, allBooks, menu);
    }

    /**
     * import all data from the file
     */
    private void importFromFile()
    {
        importShelves();
        importBooks();
        importReview();
    }

    /**
     * import shelf section from the file
     */
    private void importShelves()
    {
        while(!scan.hasNext("####"))
            allShelves.add(generateNewShelf(scan.nextLine().split("\\|")));
        scan.nextLine();
    }

    /**
     * creat a new shelf object
     * @param <info: has shelf name and may has description>
     */
    private Shelf generateNewShelf(String[] info)
    {
        if(info.length > 1) return new Shelf(info[0], info[1]); //if the array contain name and description, store both
        return new Shelf(info[0]);
    }

    /**
     * import book section from the file
     */
    private void importBooks()
    {
        Book book = null;
        String[] info = null; //info[0] is the section has book name, author and year, info[1] is the section has shelfs-on if it has
        while(!scan.hasNext("####"))
        {
            allBooks.add(book = generateNewBook(info = scan.nextLine().split("#")));
            if(info.length > 1) putBookOnShelf(info[1].split("\\|"), book);
        }
        scan.nextLine(); // clear the ####
    }

    /**
     * creat a book object
     * @param <info: has book name, author and year, may has shelfs-on>
     * @return <Book object>
     */
    private Book generateNewBook(String[] info)
    {
        String[] subInfo = info[0].split("\\|"); //subInfo has the book name, author and year
        return new Book(subInfo[0], subInfo[1], Integer.valueOf(subInfo[2]));
    }

    /**
     * put book that has the shelf into the book arraylist in that shelf object
     * and in that shelf object, this addBookOnShelf() method will also put this shelf object into the shelf arraylist in this book object
     * @param <shelfNames>
     * @param <book>
     */
    private void putBookOnShelf(String[] shelfNames, Book book)
    {
        for(String name: shelfNames)            
            for(Shelf shelf: allShelves)
                if(shelf.getName().equals(name)) //if the name from file is the same name of the shelf object
                    shelf.addBookOnShelf(book); //add book to shelf list and add shelf to book list
    }

    /**
     *  creat reivews to their book
     *  importOneReviewSection read the one review section from the file
     *  generateNewReview return review object add to review list in the book object
     */
    private void importReview()
    {
        String comment = "";
        String[] info = null;
        int index = 0;
        while(scan.hasNext())
        {
            comment = ""; //reset it to empty string, when finish reading a whole review
            info = scan.nextLine().split("\\|"); //info[0] is book it will be on, info[1] is the time stamp
            if(index != -1) allBooks.get(findBooksWithReview(info[0])).addReview(
                            generateNewReview(importOneReviewSection(comment), info[1]));
            scan.nextLine();
        }
    }

    /**
     * read the comment from the file, it gets the empty container and return the comment back until read "#"
     * @param <comment>
     * @return <comment>
     */
    private String importOneReviewSection(String comment)
    {
        while(!scan.hasNext("#")) 
            comment += ("\r\n" + scan.nextLine()); //"\r\n" to make review will look like same as they are in the file
        return comment;
    }

    /**
     * match the which book this review will be on
     * @param <bookOn>
     * @return <i: index of the book in the arraylist that this review will be store into>
     * @return <-1: if this review doesn't have book to be sent>
     */
    private int findBooksWithReview(String bookOn)
    {
        for(int i = 0, length = allBooks.size(); i < length; i++)
            if(bookOn.equals(allBooks.get(i).getTitle())) return i; //the book name in the file before time stamp will equal the book name in the arraylist
        return -1; //never return -1 in this case
    }

    /**
     * to creat a review object
     * @param <comment>
     * @param <timeStamp>
     * @return <a new Review Object>
     */
    private Review generateNewReview(String comment, String timeStamp)
    {
        return new Review(comment, timeStamp);
    }
}