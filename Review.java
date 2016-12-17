import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * this class is the entity of each review
 * @author Zilong Wang 
 * Last Modified: <11-18-2015> - <adding comments> <Zilong Wang>
 * @version 1.0
 */
public class Review implements Comparable<Review>
{
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    public String time;
    public String comment;

    public Review(String comment)
    {
        this.comment = comment; 
        stampTime();
    }

    public Review(String comment, String time)
    {
        this.comment = comment;
        this.time = time;
    }

    public String getTime(){return time;}
    
    public String getComment(){return comment;}
    
    /**
     * set up a time stamp 
     */
    private void stampTime()
    {
        this.time = timeFormat.format(new Timestamp(System.currentTimeMillis()));
    }
    
    /**
     * override the compareTo in the Comparable interface
     * TreeSer<Review> in the book class will use Comparable to put reviews in the order
     * @param <obj>
     * @return <an integer: ==0: equal, <0: this<obj, >0 this>obj >
     */
    @Override
    public int compareTo(Review tempReview)
    {
        if(this.time.equals(tempReview.time)) return this.comment.compareTo(tempReview.comment); //if time are same, compare comment
        return this.time.compareTo(tempReview.time); //compare time
    } 

    public String toString(){return "[" + time + "]" + comment;}
}