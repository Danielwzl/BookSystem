import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collection;

/**
 * this is the logic system control add, remove, move, display book or shelf
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class OperationSystem
{
    private static final String DECORATION = "   "; //adding space make everything line-up
    private ArrayList<Book> allBooks;
    private ArrayList<Shelf> allShelves;
    private GreatReadsMenu menu;

    public OperationSystem(ArrayList<Shelf> allShelves, ArrayList<Book> allBooks, GreatReadsMenu menu)
    {
        this.allShelves = allShelves;
        this.allBooks = allBooks;
        this.menu = menu;
        initialSystem();
        new ReportSystem(allBooks, allShelves); //all information goes into report center
    }

    /**
     * start the system
     */
    private void initialSystem()
    {
        boolean notQuit = false;
        int choice = 0;
        menu.welcome();
        do
        {
            menu.clear(); // clear the sub menu for display main menu
            menu.mainMenu();
            mainMenuControl(choice = menu.userChoice());
            if(notQuit = (choice != 9)) menu.pause(); //pause except quit
        }while(notQuit);
        menu.ending();
    }

    /**
     * menu control for the section of main menu control
     */
    private void mainMenuControl(int choice)
    {
        menu.clear(); // clear the menu for display sub menu
        switch(choice)
        {
            case 1:
            if(!isEmptyElement(allBooks, "books")) listAllBooks();
            break;
            case 2:
            if(!isEmptyElement(allShelves, "shelves")) listAllShelves();
            break;
            case 3:
            if(!isEmptyElement(allShelves, "shelves"))
            {
                menu.detailMenu(allShelves, "Shelves");
                listShelfDetail(menu.userChoice() - 1); //get index
            }
            break;
            case 4:
            if(!isEmptyElement(allBooks, "books"))
            {
                menu.detailMenu(allBooks, "Books");
                listBookDetail(menu.userChoice() - 1); //get index
            }
            break;
            case 5:
            menu.subMenu("book");
            subBookMenuControl(); //add or remove book
            break;
            case 6:
            menu.subMenu("shelf");
            subShelfMenuControl(); //add or remove shelf
            break;
            case 7:
            menu.subMenu("review");
            addReview();
            break;
            case 8:
            menu.subMenu("book from a shelf"); 
            subMovingMenuControl(); //move book on or off
            break;
        }
    }

    /**
     * menu control for the section of adding books
     */
    private void subBookMenuControl()
    {
        switch(menu.userChoice())
        {
            case 1:
            addBook();
            break;
            case 2:
            removeBook();
            break;
        }
    }

    /**
     * menu control for the section of adding shelves
     */
    private void subShelfMenuControl()
    {
        switch(menu.userChoice())
        {
            case 1:
            addShelf();
            break;
            case 2:
            removeShelf();
            break;
        }
    }

    /**
     * menu control for the section of moving books to other shelves
     */
    private void subMovingMenuControl()
    {
        switch(menu.userChoice())
        {
            case 1:
            bookOnShelf(); //move book on a shelf
            break;
            case 2:
            bookOffShelf(); //move book off from a shelf
            break;
        }
    }

    /**
     * list all books
     * @return <index: last index + 1 in the book arraylist>
     */
    private int listAllBooks()
    {
        int index = 0;
        organizeBooks();
        menu.headerOfBookList(); //print header
        for(Book book: allBooks)
            menu.showAllInfo(translateToUserNumber(index++) + ". " + book);
        return index;
    }

    /**
     * list all shelves
     * @return <index: last index + 1 in the shelf arraylist>
     */
    private int listAllShelves()
    {
        int index = 0;
        menu.headerOfShelfList(); //print header
        for(Shelf shelf: allShelves)
            menu.showAllInfo(translateToUserNumber(index++) + ". " + shelf);
        return index;
    }

    /**
     * show the detail of this shelf
     * @param <indexOfShelf>
     */
    private void listShelfDetail(int indexOfShelf)
    {
        int index = 0;
        Shelf targetShelf = allShelves.get(indexOfShelf); //get this shelf from user input
        ArrayList<Book> booksOnThisShelf = targetShelf.getBooksOnThisShelf(); //get the books on this shelf
        menu.headerOfShelfList(); //header
        menu.showAllInfo(DECORATION + targetShelf); //show information of this shelf
        menu.headerOfBookList(); //header
        for(Book book: booksOnThisShelf)
            menu.showAllInfo(translateToUserNumber(index++) + ". " + book); //show the books on this shelf
    }

    /**
     * show the detail of this book
     * @param <indexOfBook>
     */
    private void listBookDetail(int indexOfBook)
    {
        Book tempBook = allBooks.get(indexOfBook); //get the book from user input
        menu.header("\n-- Shelves --\n|"); //header
        listShelfSection(tempBook.getShelvesHasThisBook()); //list all shelves that has this book
        menu.header("\n\n-- Book --\n"); //header, return to new line from shelf section one more time
        listBookSection(tempBook); //list the detail of this book
        menu.header("\n-- Review --\n"); //header
        listReviewSection(tempBook.getReview()); //list the review of this book 
    }

    /**
     * list the shelfs that this book on
     * @param <tempShelf>
     */
    private void listShelfSection(ArrayList<Shelf> tempShelf)
    {
        if(!isEmptyElement(tempShelf, "shelves")) //check if there is shelves that has this book
            for(Shelf shelf: tempShelf)
                menu.showAllInfoAtSameLine(shelf.getName() + "|"); //show the shelves
    }

    /**
     * show this book
     * @param <tempbook>
     */
    private void listBookSection(Book tempBook)
    {
        menu.headerOfBookList(); //header
        menu.showAllInfo(DECORATION + tempBook); //show this book
    }

    /**
     * list the review section that this book has
     * @param <tempReviews>
     */
    private void listReviewSection(TreeSet<Review> tempReviews)
    {
        if(!isEmptyElement(tempReviews, "review")) //check if there is review on this book
        {
            int order = 0;
            for(Review review: tempReviews)
                menu.showAllInfo(++order + ". " + review);  //show review
        }
    }

    /**
     * bubble sort, put book by sorting their years
     */
    private void organizeBooks()
    {
        for(int i = 0, length = allBooks.size(); i < length - 1; i++)
            for(int j = 0; j < length - i - 1; j++)
                swap(j, j + 1);
    }

    /**
     * swap the position of books
     * @param <lowIndex>
     * @param <highIndex>
     */
    private void swap(int lowIndex, int highIndex)
    {
        Book tempBook = null, currentBook = allBooks.get(lowIndex), nextBook = allBooks.get(highIndex);
        if(currentBook.getYear() > nextBook.getYear())
        {
            tempBook = currentBook;
            allBooks.set(lowIndex, nextBook);
            allBooks.set(highIndex, tempBook);
        }
    }

    /**
     * add a book to system
     */
    private void addBook()
    {
        Book tempBook = menu.addBookMenu(); //get new book from use input
        if(!allBooks.contains(tempBook)) //check if there is book with same name in the system
        {
            allBooks.add(tempBook); 
            menu.headerOfBookList();
            menu.showAllInfo(DECORATION + tempBook); //print this added-book detail
        }
        else menu.warnning("Book existed in the system!");
    }

    /**
     * add a shelf to system
     */
    private void addShelf()
    {
        Shelf tempShelf = menu.addShelfMenu(); //get new shelf from use input
        if(!allShelves.contains(tempShelf)) //check if there is shelf with same name in the system
        {
            allShelves.add(tempShelf);
            menu.headerOfShelfList();
            menu.showAllInfo(DECORATION + tempShelf); //print this added-book detail
        }
        else menu.warnning("Shelf existed in the system!");
    }

    /**
     * list the books to user, get the index of book they like to remove, remove a book from system
     */
    private void removeBook()
    {
        if(!isEmptyElement(allBooks, "books")) //check if there is book in the system that user can remove
            removeBookOnShelf(allBooks.remove(getElementIndex(listAllBooks(), "book"))); //remove it from allBooks and Shelf object
    }

    /**
     * list the shelves to user, get the index of shelf they like to remove, remove a shelf from system
     */
    private void removeShelf()
    {
        if(!isEmptyElement(allShelves, "shelves")) //check if there is shelf in the system that user can remove
            removeBookOnShelf(allShelves.remove(getElementIndex(listAllShelves(), "shelve"))); //remove it from allShlevs and Book object
    }

    /**
     * remove book history in the shelf object from the arraylist of books-has
     * param <removeBook>
     */
    private void removeBookOnShelf(Book removeBook)
    {
        for(Shelf shelf: allShelves)
            shelf.removeBook(removeBook); //remove the this book from every shelf that has it
    }

    /**
     * remove shelf history in the book object from the arraylist of shelves-on
     * param <removeShelf>
     */
    private void removeBookOnShelf(Shelf removeShelf)
    {
        for(Book book: allBooks)
            book.removeShelfHasTheBook(removeShelf); //remove the this shelf from every book that has it
    }

    /**
     * list all book to let user to choose which one they want to comment
     * and let them add review in that book
     */
    private void addReview()
    {
        if(!isEmptyElement(allBooks, "book"))
        {
            allBooks.get(menu.selectElementMenu( //list all book to let user to choose which one they want to comment
                    listAllBooks(), "book you want to comment")).addReview(menu.addReviewMenu()); //get comment from user input and add it to that book
        }
    }

    /**
     * list all book to let user to choose which one they want to move
     * and then to move book on the shelf
     */
    private void bookOnShelf()
    {
        if(!isEmptyElement(allBooks, "book") && !isEmptyElement(allShelves, "shelf"))
        {
            Book tempBook = allBooks.get(getElementIndex(listAllBooks(), "book you like to move")); //get the book
            int indexOfShelf = getElementIndex(listAllShelves(), "shelve you like to move this book on"); //get the index of shelf 
            Shelf tempShelf = allShelves.get(indexOfShelf); //get shelf
            if(!tempBook.getShelvesHasThisBook().contains(tempShelf)) 
            {
                tempShelf.addBookOnShelf(tempBook); //if this book was not on this shelf, add it on shelf, and add shelf to book
                confirmation(indexOfShelf); //show detail of this shelf
            }
            else menu.warnning("This book is already on this shelf!");
        }
    }

    /**
     * list all book to let user to choose which one they want to move
     * and then to move book off the shelf
     */
    private void bookOffShelf()
    {
        if(!isEmptyElement(allBooks, "book") && !isEmptyElement(allShelves, "shelf"))
        {
            Book tempBook = allBooks.get(getElementIndex(listAllBooks(), "book you like to move")); //get the book 
            int indexOfShelf = getElementIndex(listAllShelves(), "shelve you like to move this book off"); //get the index of shelf 
            Shelf tempShelf = allShelves.get(indexOfShelf); //get shelf
            if(tempBook.getShelvesHasThisBook().contains(tempShelf)) 
            {
                tempShelf.removeBookOnShelf(tempBook); //if this book was on this shelf, move it off, and remove book from shelf too
                confirmation(indexOfShelf); //show detail of this shelf
            }
            else menu.warnning("This book is not on this shelf!");
        }
    }

    /**
     * after user moved book, show them the this shelf detail
     */
    private void confirmation(int indexOfShelf)
    {
        menu.header(String.format("\n%70s\n", "< Move compelete >")); //header
        listShelfDetail(indexOfShelf); //show this shelf
    }

    /**
     * let user choose one element
     * @param <lastElement>
     * @param <message>
     * @return <index of book that user chose>
     */
    private int getElementIndex(int lastElement, String message)
    {
        return menu.selectElementMenu(lastElement, message); 
    }

    /**
     * check if there is any element inside of arraylist
     * @param <list>
     * @param <message>
     * @return <true if arraylist is empty>
     */
    private <T> boolean isEmptyElement(Collection<T> list, String message)
    {
        boolean empty = false; 
        if(empty = list.isEmpty()) menu.showAllInfo("[No " + message + " in the system]");
        return empty; //true: no element(empty), false: has element(not empty)
    }

    /**
     * convert the index in arraylist to the number that user prefer to see
     * @param <systemIndex>
     * @return <number: starts with 1>
     */
    private int translateToUserNumber(int systemIndex){return systemIndex + 1;}
}