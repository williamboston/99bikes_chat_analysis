import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChatAnalytic {
    public static void main(String args[]) {

        // array of known tags
        String[] tags = {"frequent_visitor", "mtb", "road", "bmx", "sale", "commuter", "e-bike", "scooter", "2hb"};
        // list of found tags
        List<String> finalTags = new ArrayList<String>();

        //first args is file name (locally)
        String csv = args[0];
        BufferedReader file = null;
        String line = "";
        String line2 = "";
        String delim = ",";

        // block to find all valid tags from given csv file
        try {
            file = new BufferedReader(new FileReader(csv));
 
            //need a way to distinguish between commas in tags col and csv commas
            while ((line = file.readLine()) != null) {
                String[] tag = line.split(delim);
                //clean the strings of quote marks 
                for (String retVal : tag) {
                    if (retVal.startsWith("\"") || retVal.endsWith("\"")) {
                        retVal = retVal.replace("\"", "");
                        finalTags.add(retVal);
                    }
                }
                // check for match
                for (String ret : tag) {
                    for (String match : tags) {
                        if (ret.equals(match)) {
                            finalTags.add(ret);
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

        //for testing
        for (String tag : finalTags) {
            System.out.println(tag);
        }

        // open existing csv
        try {
            FileWriter output = new FileWriter("chat_counter.csv", true);

            for (String tag : finalTags) {
                output.append(tag);
                output.append("\n");
            }

            output.close();
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
        // 


    }
}