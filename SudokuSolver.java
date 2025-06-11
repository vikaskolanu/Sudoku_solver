import java.io.*;
import java.util.*;

public class SudokuSolver {

    static boolean isSafe(int[][] board, int row, int col, int digit) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == digit || board[row][i] == digit) return false;
        }

        int sr = (row / 3) * 3, sc = (col / 3) * 3;
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (board[i][j] == digit) return false;
            }
        }
        return true;
    }

    static boolean solve(int[][] board, int row, int col) {
        if (row == 9) return true;

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;

        if (board[row][col] != 0) {
            return solve(board, nextRow, nextCol);
        }

        for (int digit = 1; digit <= 9; digit++) {
            if (isSafe(board, row, col, digit)) {
                board[row][col] = digit;
                if (solve(board, nextRow, nextCol)) return true;
                board[row][col] = 0;
            }
        }

        return false;
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int num : row) System.out.print(num + " ");
            System.out.println();
        }
    }

    static int[][] loadFromFile(String filePath) throws IOException {
        int[][] board = new int[9][9];
        Scanner scanner = new Scanner(new File(filePath));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        return board;
    }

    public static void main(String[] args) {
        try {
            String filePath = "sudoku.txt";  // Just update this file to change input
            int[][] board = loadFromFile(filePath);
            System.out.println("Input Sudoku:");
            printBoard(board);

            if (solve(board, 0, 0)) {
                System.out.println("\nSolved Sudoku:");
                printBoard(board);
            } else {
                System.out.println("\nNo solution exists.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
        }
    }
}
