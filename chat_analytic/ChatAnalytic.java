import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChatAnalytic {

    private static Scanner x;

    public static void main(String args[]) {


        // array of known tags
        String[] tags = {"frequent_visitor", "mtb", "road", "bmx", "sale", "commuter", "e-bike", "scooter", "2hb"};
        // list of found tags
        List<String> finalTags = new ArrayList<String>();
        List<String> currentTags = new ArrayList<String>();

        //  first args is file name (locally)
        // Todo: add ability to give path as args
        String csv = args[0];
        BufferedReader file = null;
        BufferedReader output = null;
        String line, line2 = "";
        String delim = ",";

        // block to find all valid tags from given csv file
        try {
            System.out.println("\nBeginning Import...");

            file = new BufferedReader(new FileReader(csv));
 
            while ((line = file.readLine()) != null) {
                String[] tag = line.split(delim);

                //clean the strings of quote marks - annoying formatting by Zendesk
                for (String retVal : tag) {
                    if (retVal.startsWith("\"") || retVal.endsWith("\"")) {
                        retVal = retVal.replace("\"", "");
                        for (String match : tags) {
                                if (retVal.equals(match)) {
                                    finalTags.add(retVal);
                                }
                            }
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
            System.out.println("Import Complete!\n");


        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // block to open existing csv and write changes to it
        try {
            System.out.println("Beginning Export...");

            output = new BufferedReader(new FileReader("chat_counter.csv"));

            /* !! PART 1: find and add new tags to the output csv !! */

            //first create a list of current tags
            while ((line2 = output.readLine()) != null) {
                String[] vals = line2.split(delim);
                for (String val : vals) {
                    currentTags.add(val);
                }
            }

            // temp list - to be used to find new tags
            List<String> tempList = new ArrayList<String>(finalTags);
            // duplicate list - to be used to store non-duplicates
            List<String> dupesList = new ArrayList<String>();

            //compare list of current tags with list of given tags - remove to isolate only new tags
            for (String currentTag : currentTags) {
                for (String newTag : finalTags) {
                    if (newTag.equals(currentTag)) {
                        tempList.remove(newTag);
                    }
                }
            }

            // create new list without duplicates
            for (String tag : tempList) {
                if (!dupesList.contains(tag)) {
                    dupesList.add(tag);
                }
            }

            // write new tags to csv, along with initial count of 0
            for (String newTag : dupesList) {
                System.out.println(newTag);
                FileWriter update = new FileWriter("chat_counter.csv", true);
                BufferedWriter out = new BufferedWriter(update);
                out.append(newTag);
                out.append(",");
                out.append(Integer.toString(0));
                out.append("\n");
                out.close();
            }

            /* !! PART 2: call method to update the count for every new tag !! */

            // write updated counts
            for (String tag : finalTags) {
                updateCount("chat_counter.csv", tag);
            }

        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Export Complete!\n");
    }



    // called to 'update' the csv with new count
    public static void updateCount(String filename, String tagName) {
        String temp = "temp.csv";
        File oldFile = new File(filename);
        File newFile = new File(temp);
        String tag = "";
        String thisCount = "";

        try {
            FileWriter fw = new FileWriter(temp, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filename));
            x.useDelimiter("[,\n]");

            while(x.hasNext()) {
                tag = x.next();
                thisCount = x.next();

                // if this tag matches, increment the existing count by 1
                if (tag.equals(tagName)) {
                    int countHere = Integer.parseInt(thisCount);
                    countHere++;
                    String newCount = Integer.toString(countHere);
                    pw.println(tagName + "," + newCount);
                }
                // else just stay the same
                else {
                    pw.println(tag + "," + thisCount);
                }
            }

            // clean up and replace old file with new file
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filename);
            newFile.renameTo(dump);

        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}