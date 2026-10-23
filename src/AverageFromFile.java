import java.io.*;

public class AverageFromFile {
    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "data/fileio/numbers.txt";
        double sum = 0;
        int count = 0;

//attempt to make reader or go go to attached catch. close the socket in either case
        try (BufferedReader br =
                new BufferedReader(new FileReader(fileName))) {

            String line = "";

            while ((line = br.readLine()) != null) { // read one at a time entire file; null ony lf or the very last line of the file

                //convert to double unless bad format, then skip
                try {
                    double num = Double.parseDouble(line.trim()); // (good habit) allows ints and doubles to be included; trim causes the spaces/indents to be removed
                    sum += num;
                    count++;
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping invalid number: " + line);
                }
            }

            if (count > 0) {
                double average = sum / count;
                System.out.println("Average of numbers = " + average);
            }
            else {
                System.out.println("No valid numbers found in the file.");
            }
        }
        catch (IOException io) {
            System.err.println("File Error: " + io);
        }
    }
}