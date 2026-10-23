import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class TemperatureReport {
    public static void main(String[] args) throws IOException {
        String fileName = args.length > 0 ? args[0] : "data/fileio/temperatures.txt";
        int windowSize;
        ArrayList<Integer> temperatures = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // First line means DAYS PER WINDOW, not a temperature.
            windowSize = Integer.parseInt(br.readLine().trim());
            String line;
            while ((line = br.readLine()) != null) {
                temperatures.add(Integer.parseInt(line.trim()));
            }
        }

        // Start by calculating the sum of the first complete window.
        long currentSum = 0;
        for (int i = 0; i < windowSize; i++) {
            currentSum += temperatures.get(i);
        }
        long bestSum = currentSum;
        int bestStartIndex = 0;

        // Shift right one day: add the entering value, subtract the leaving one.
        for (int endIndex = windowSize; endIndex < temperatures.size(); endIndex++) {
            currentSum += temperatures.get(endIndex);
            currentSum -= temperatures.get(endIndex - windowSize);

            // Strict > retains the EARLIEST window when tied.
            if (currentSum > bestSum) {
                bestSum = currentSum;
                bestStartIndex = endIndex - windowSize + 1;
            }
        }

        double highestAverage = (double) bestSum / windowSize;
        System.out.printf(Locale.US, "Highest Average: %.1f%n", highestAverage);
        System.out.println("Days: " + (bestStartIndex + 1) + "-" +
                           (bestStartIndex + windowSize));
    }
}
