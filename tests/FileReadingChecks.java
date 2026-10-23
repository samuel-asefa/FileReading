import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class FileReadingChecks {
    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void equal(Object expected, Object actual, String message) {
        check(Objects.equals(expected, actual), message + ": expected " + expected + ", got " + actual);
    }

    private static String normalized(String text) {
        return text.replace("\r\n", "\n");
    }

    private static String run(String className, String keyboard, String... args) throws Exception {
        ArrayList<String> command = new ArrayList<>();
        command.add(Path.of(System.getProperty("java.home"), "bin", "java").toString());
        command.add("-cp");
        command.add(System.getProperty("java.class.path"));
        command.add(className);
        command.addAll(Arrays.asList(args));
        Process process = new ProcessBuilder(command).start();
        process.getOutputStream().write(keyboard.getBytes(StandardCharsets.UTF_8));
        process.getOutputStream().close();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String errors = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        equal(0, process.waitFor(), className + " process exit (" + errors + ")");
        return normalized(output);
    }

    private static void checkFileTasks(Path temporary) throws Exception {
        String[] names = {"LucasNumbers", "PlausiblePolygon", "TicTacTally", "TemperatureReport", "RangeTotals"};
        for (String name : names) {
            String expected = Files.readString(Path.of("expected-output/fileio/" + name + "_output.txt"));
            equal(normalized(expected), run(name, ""), name + " worksheet output");
        }
        equal("Average of numbers = -88.1\n", run("AverageFromFile", ""), "AverageFromFile sample");

        Path lucas = temporary.resolve("lucas.txt");
        Files.writeString(lucas, "0\n10\n2\n10\n");
        equal("2\n123\n3\n123\n", run("LucasNumbers", "", lucas.toString()), "Lucas repeated/out-of-order indices");

        Path temperatures = temporary.resolve("temperatures.txt");
        Files.writeString(temperatures, "2\n-9\n-7\n-9\n");
        equal("Highest Average: -8.0\nDays: 1-2\n", run("TemperatureReport", "", temperatures.toString()),
                "Negative temperatures and earliest tied window");

        Path totals = temporary.resolve("totals.txt");
        Files.writeString(totals, "3\n2000000000\n2000000000\n-1\n3\n0,1\n0,2\n2,2\n");
        equal("4000000000\n3999999999\n-1\n", run("RangeTotals", "", totals.toString()), "Long range totals");

        Path polygon = temporary.resolve("polygons.txt");
        Files.writeString(polygon, "4,5,9|60,60,60\n2.5,3,4|60,60,60\n");
        equal("4,5,9|60,60,60 is NOT a polygon\n2.5,3,4|60,60,60 is possibly a polygon\n",
                run("PlausiblePolygon", "", polygon.toString()), "Strict polygon inequality and decimal sides");
        System.out.println("PASS: six file-reading programs and worksheet/edge-case outputs");
    }

    public static void main(String[] args) throws Exception {
        Path temporary = Files.createTempDirectory("topic-checks-");
        try {
            checkFileTasks(temporary);
            System.out.println("All " + checks + " checks passed.");
        } finally {
            try (java.util.stream.Stream<Path> paths = Files.walk(temporary)) {
                for (Path path : paths.sorted(java.util.Comparator.reverseOrder()).toArray(Path[]::new)) {
                    Files.deleteIfExists(path);
                }
            }
        }
    }
}
