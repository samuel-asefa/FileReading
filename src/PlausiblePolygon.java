import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlausiblePolygon {
    // Turn a comma-separated group like "12,18,9" into a numeric array.
    private static double[] parseNumbers(String group) {
        String[] tokens = group.split(",");
        double[] values = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            values[i] = Double.parseDouble(tokens[i].trim());
        }
        return values;
    }

    // Enforce precisely the four conditions listed in the assignment.
    private static boolean meetsFourCriteria(String line) {
        // The pipe is special in regex. "\\|" is its Java-string spelling.
        // The resulting regular expression matches one literal pipe.
        String[] groups = line.split("\\|");
        double[] sides = parseNumbers(groups[0]);
        double[] angles = parseNumbers(groups[1]);

        if (sides.length < 3 || angles.length < 3) return false;
        if (sides.length != angles.length) return false;

        double angleSum = 0;
        for (double angle : angles) angleSum += angle;
        double requiredAngleSum = 180.0 * (angles.length - 2);
        if (Math.abs(angleSum - requiredAngleSum) > 1e-9) return false;

        double sideSum = 0;
        double largestSide = Double.NEGATIVE_INFINITY;
        for (double side : sides) {
            sideSum += side;
            if (side > largestSide) largestSide = side;
        }
        return largestSide < sideSum - largestSide;
    }

    public static void main(String[] args) throws IOException {
        String fileName = args.length > 0 ? args[0] : "data/fileio/polygons.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line + (meetsFourCriteria(line)
                    ? " is possibly a polygon" : " is NOT a polygon"));
            }
        }
    }
}
