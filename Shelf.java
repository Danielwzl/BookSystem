import java.util.ArrayList;

/**
 * This is class is the entity of each shelf
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class Shelf
{
    private String name, description = "";
    private int numberOfBook;
    private ArrayList<Book> booksOnThisShelf;
    
    public Shelf(String name)
    {
        this.name = name;
        booksOnThisShelf = new ArrayList<Book>();
    }

    public Shelf(String name, String description)
    {
        this(name);
        this.description = description;
    }

    public String getName(){return name;}

    public String getDescription(){return description;}

    public ArrayList<Book> getBooksOnThisShelf(){return booksOnThisShelf;}
    
    /**
     * Dependency Injection
     * remove book both from shelf and book object 
     * @param <book>
     */
    public void removeBookOnShelf(Book book)
    {
        booksOnThisShelf.remove(book);
        book.getShelvesHasThisBook().remove(this);
    }
    
    /**
     * remove book only from shelf object
     * @param <book>
     */
    public void removeBook(Book book)
    {
        booksOnThisShelf.remove(book);
    }

    /**
     * add new book into arraylist
     * @param <book>
     */
    public void addBookOnShelf(Book book)
    {
        booksOnThisShelf.add(book);
        book.getShelvesHasThisBook().add(this); //add this shelf to the book as well
    }
    
    /**
     * see if two shelf are the same one
     * @param <obj>
     * @return <true if they are same>
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Shelf)) return false;
        return this.name.equals(((Shelf) obj).name);
    }

    public String toString()
    {
        return String.format("%-68s%23d", name + " [" + description + "]", booksOnThisShelf.size());
    }
}
