import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RangeTotals {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("rangeTotals.txt"))) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] prefixTotals = new long[n + 1]; // prefixTotals[0] = 0

            // Prefix index i+1 is the sum of data values at indices 0..i.
            for (int i = 0; i < n; i++) {
                int value = Integer.parseInt(br.readLine().trim());
                prefixTotals[i + 1] = prefixTotals[i] + value;
            }

            int q = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < q; i++) {
                String query = br.readLine();
                int comma = query.indexOf(','); // no regex or split()
                int start = Integer.parseInt(query.substring(0, comma).trim());
                int end = Integer.parseInt(query.substring(comma + 1).trim());
                long total = prefixTotals[end + 1] - prefixTotals[start];
                System.out.println(total); // exactly one unlabeled total per line
            }
        }
    }
}
