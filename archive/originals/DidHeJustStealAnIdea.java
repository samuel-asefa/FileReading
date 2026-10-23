import java.io.*;

// FILE READ TASK 1
public class DidHeJustStealAnIdea {
	public static void main(String[] args) {
		String fileName = "lucas.txt";

		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {

		} catch (IOException io) {
			System.err.println("File Error: " + io);
		}
	}

}