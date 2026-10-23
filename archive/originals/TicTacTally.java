import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TicTacTally {
    // Positions below are ARRAY INDICES (0 through 8), not input positions 1 through 9.
    private static final int[][] WINNING_LINES = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
        {0, 4, 8}, {2, 4, 6}             // diagonals
    };

    private static boolean hasWon(char[] board, char player) {
        for (int[] line : WINNING_LINES) {
            if (board[line[0]] == player &&
                board[line[1]] == player &&
                board[line[2]] == player) {
                return true;
            }
        }
        return false;
    }

    // 'X' or 'O' if somebody wins; 'D' if no player wins.
    private static char determineOutcome(String game) {
        char[] board = new char[9];
        Arrays.fill(board, ' ');
        char currentPlayer = game.charAt(0);

        for (int i = 1; i < game.length(); i++) {
            int boardIndex = game.charAt(i) - '1'; // input 1 -> index 0
            board[boardIndex] = currentPlayer;

            if (hasWon(board, currentPlayer)) {
                return currentPlayer; // game ends as soon as someone wins
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        return 'D';
    }

    public static void main(String[] args) throws IOException {
        int xWins = 0;
        int oWins = 0;
        int draws = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("TicTac.txt"))) {
            String game;
            while ((game = br.readLine()) != null) {
                char result = determineOutcome(game.trim());
                if (result == 'X') xWins++;
                else if (result == 'O') oWins++;
                else draws++;
            }
        }
        System.out.printf("X-Wins = %d, O-Wins = %d, Draws = %d%n",
                          xWins, oWins, draws);
    }
}
