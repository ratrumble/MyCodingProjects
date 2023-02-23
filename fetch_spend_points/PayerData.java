import java.text.*;
import java.util.*;

/**
 * A class defined for payer data
 *
 * @author (Eric Liu)
 * @version (a version number or a date)
 */
public class PayerData implements Comparable<PayerData>
{
    // Payer Name
    private String payerName = "";
    private int points = 0;
    private String timeStamp = "";
    private long timeStampInMilliSeconds = 0;
    private boolean valid = false;
    
    /**
     * Constructor to initialize an object with an input string
     */
    public PayerData(String inputLine)
    {
        valid = init(inputLine);
    }

    /*********************************************** 
     * getters
     **********************************************/
    public String getName()
    {
        return payerName;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    /**************************************
     * Setters
     *************************************/
    public void setPoints(int newPoints)
    {
        points = newPoints;
    }
    
    /**
     * Implement Comparable interface
     */
    public int compareTo(PayerData data){
        return (int)(timeStampInMilliSeconds/1000 - data.timeStampInMilliSeconds/1000);
    }
    
    /**
     * Initialize this object
     */
    private boolean init(String input)
    {
        // make sure input is not null
        if(input == null)
        {
            System.out.println("This line is null/empty");
            return false;
        }
        
        String[] data = input.split(",");
        // Make sure it has 3 parts - name, points, timeStamp
        if(data.length != 3)
        {
            System.out.println("This line is invalid : input = " + input);
            return false;
        }
        
        payerName = data[0].trim();

        try {
            points = Integer.parseInt(data[1].trim());
        }
        catch (Exception e)
        {
            return false;
        }
        
        // convert and save time stamp
        timeStamp = data[2].trim();
        // input timeStamp has leading and ending '"', need to remove them to get parse right
        timeStamp = timeStamp.substring(1, timeStamp.length() - 1);
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = dateFormat.parse(timeStamp);
            timeStampInMilliSeconds = date.getTime();
        } catch (Exception e)
        {
            System.out.println(e + " " + timeStamp);
            return false;
        }
        return true;
    }
}
