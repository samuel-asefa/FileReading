import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class LucasNumbers {
    public static void main(String[] args) throws IOException {
        String fileName = args.length > 0 ? args[0] : "data/fileio/lucas.txt";
        // INDEX REMINDER: L(0) = 2 and L(1) = 1.
        // BigInteger is necessary because L(200) does not fit in a long.
        ArrayList<BigInteger> lucas = new ArrayList<>();
        lucas.add(BigInteger.valueOf(2)); // index 0
        lucas.add(BigInteger.ONE);        // index 1

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int n = Integer.parseInt(line.trim());

                // Reuse earlier terms. Generate only those we have not computed.
                while (lucas.size() <= n) {
                    int last = lucas.size() - 1;
                    BigInteger next = lucas.get(last).add(lucas.get(last - 1));
                    lucas.add(next);
                }

                System.out.println(lucas.get(n));
            }
        }
    }
}
