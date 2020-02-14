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
                System.out.println("Tag = " + tag[31]);
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