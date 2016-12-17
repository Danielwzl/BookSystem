import java.util.ArrayList;
import java.util.TreeSet;

/**
 * this class is the entity of each book
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class Book
{
    private String title, author;
    private int year;
    private TreeSet<Review> allReviews;
    private ArrayList<Shelf> shelfHasThisBook; //get the time of comment in natual order

    public Book(String title, String author, int year)
    {
        this.title = title;
        this.author = author;
        this.year = year;
        allReviews = new TreeSet<Review>();
        shelfHasThisBook = new ArrayList<Shelf>();
    }

    public String getTitle(){return title;}

    public TreeSet<Review> getReview(){return allReviews;}

    public int getYear(){return year;}

    public String getAuthor(){return author;}

    public ArrayList<Shelf> getShelvesHasThisBook(){return shelfHasThisBook;}

    public void setShelvesHasThisBook(ArrayList<Shelf> shelfHasThisBook){this.shelfHasThisBook = shelfHasThisBook;}

    /**
     * remove shelf from arraylist
     * @param <shelfForRemove>
     */
    public void removeShelfHasTheBook(Shelf shelfForRemove)
    {
        shelfHasThisBook.remove(shelfForRemove); 
    }

    /**
     * add review
     * @param <review>
     */
    public void addReview(Review review){allReviews.add(review);}

    /**
     * check if two book are the same one
     * @param <obj>
     * @return <true if they are same one>
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Book)) return false;
        return this.title.equals(((Book) obj).title);
    }

    public String toString()
    {
        return String.format("%-40s%-20s%16d%15d%20d", title, "      " + author, year, shelfHasThisBook.size(),
            allReviews.size());
    }
}