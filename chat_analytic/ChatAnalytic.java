import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChatAnalytic {
    public static void main(String args[]) {
        // open csv
        // scan for certain tags
        // append to list or something
        // append those numbers to a local database if you're into that OR another CSV (this might make it easier to display rather than having to then read the DB and display in the terminal or some shit)
        System.out.println("code compiling and running");

        String[] tags = {"frequent_visitor", "mtb", "road", "bmx", "sale", "commuter", "e-bike", "scooter", "2hb", };
        // Tag Array
        // String[] tagStrings;

        //first args is file name (locally)
        String csv = args[0];
        BufferedReader file = null;
        String line = "";
        String csvSplitBy = ",";

        try {
            file = new BufferedReader(new FileReader(csv));

            //need a way to distinguish between commas in tags col and csv commas
            while ((line = file.readLine()) != null) {
                String[] tag = line.split(csvSplitBy);
                //clean the strings of quote marks
                for (String retVal : tag) {
                    if (retVal.startsWith("\"") || retVal.endsWith("\"")) {
                        retVal = retVal.replace("\"", "");
                        // System.out.println("string cleaned...");
                    }
                }
                // check for match
                for (String ret : tag) {
                    for (String match : tags) {
                        if (ret.equals(match)) {
                            System.out.println(ret);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}