import java.util.*;

/**
 * A class defined to manage points for a user.
 *
 * @author (Eric Liu)
 * @version (a version number or a date)
 */
public class PointManager
{
    // Use a TreeSet here so that it has all PayerData in the order of timeStamp.
    // it takes O(logN) time to add a payer data
    TreeSet<PayerData> payerDataSet = null;

    /**
     * Constructor for objects of class PointManager
     */
    public PointManager()
    {
        payerDataSet = new TreeSet<PayerData>();
    }

    public void addPayerData(PayerData data)
    {
        if(data == null || !data.isValid())
        {
            return;
        }
        payerDataSet.add(data);
    }
    
    public void spendPoints(int pointsToSpend)
    {
        if(pointsToSpend <= 0)
        {
            System.out.println("points to spend should be a positive number");
            return;
        }
        
        // iterate payerDataSet and deduct points from each entry if necessary
        // NOTES that payerDataSet is a tree set sorted by time stamp - It mean we
        // spend the oldest points first
        for(PayerData data : payerDataSet)
        {
            if(pointsToSpend <= 0)
            {
                // points have been deducted completely, nothing more to do
                break;
            }
            
            int payerPoints = data.getPoints();
            if(payerPoints >= pointsToSpend)
            {
                // enough to deduct for entire pointsToSpend
                int newPayerPoints = payerPoints - pointsToSpend;
                data.setPoints(newPayerPoints);
                pointsToSpend = 0;
            }
            else
            {
                // Deduct partial pointsToSpend
                data.setPoints(0);
                pointsToSpend = pointsToSpend - payerPoints;
            }
        }
    }
    
    public void printOutPayerData()
    {
        // use HashMap here store balances of payers since we can access each entry instantly O(1) with 
        // payerName as the key.
        // HashMap<payerName, totalPoints>
        HashMap<String, Integer> payerPointsMap = new HashMap<String, Integer>();
        
        // iterate to get total points for each payer
        for(PayerData data : payerDataSet)
        {
            String payerName = data.getName();
            
            // if we had an entry in the map with the same name, we need to add them together
            if(payerPointsMap.containsKey(payerName))
            {
                payerPointsMap.put(payerName, payerPointsMap.get(payerName) + data.getPoints());
            }
            else
            {
                payerPointsMap.put(payerName, data.getPoints());
            }
        }
        
        // print them out
        System.out.println("{");
        for(Map.Entry<String, Integer> entry : payerPointsMap.entrySet())
        {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("}");
    }
}
