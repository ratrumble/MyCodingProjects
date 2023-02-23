import java.io.*;

/**
 * Main class to run this program.
 *
 * @author (Eric Liu)
 * @version (a version number or a date)
 */
public class Main
{
        public static void main(String[] args) {

        // Check command line
        if (args.length != 2) {
            System.out.println("Usage: java Main points <filename>");
            return;
        }

        // Make sure the input file exist
        File file = new File(args[1]);
        if (!file.exists()) {
            System.out.println("File " + args[1] + " does not exist.");
            return;
        }
        
        // Parse points into an integer
        int pointsToSpend = 0;
        try {
            pointsToSpend = Integer.parseInt(args[0]);
        }
        catch (Exception e)
        {
            System.out.println("Points to spend is not a valid integer number");
            return;
        }
        
        if(pointsToSpend < 0)
        {
            System.out.println("Points to spend can not be negative");
            return;
        }
        
        
        // Instantiate PointManager
        PointManager pointManager = new PointManager();
        
        // process input
        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Parse each line into a PayerData
                PayerData payerData = new PayerData(line);
                
                // The line might not be a valid input for payer data - e.g. first line is
                // "payer", "points", "timerStamp" which is not a valid data
                if(payerData.isValid())
                {
                    pointManager.addPayerData(payerData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // spend points
        pointManager.spendPoints(pointsToSpend);
        
        // print output
        pointManager.printOutPayerData();
    }
}
