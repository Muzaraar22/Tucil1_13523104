package src;

import java.util.*;


public class Solver {
    public static int[] findNextEmpty(char[][] board) {
        int[] pos = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == ' ') {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return null;
    }

    public static boolean isFit(char[][] board, char blockChar, List<int[]> block, int row, int col) {
        for (int[] pos : block) {
            int newRow = row + pos[0];
            int newCol = col + pos[1];
            if (newRow < 0 || newRow >= board.length || newCol < 0 || newCol >= board[0].length) {
                return false;
            }
            if (board[newRow][newCol] != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean solvePuzzle(Puzzle puzzle) {
        List<Character> blockChars = new ArrayList<>(puzzle.blockMap.keySet());
        //printBoard(puzzle.board);
        
        int[] pos = findNextEmpty(puzzle.board);
        if (pos == null) {
            return true;
        }
        int row = pos[0];
        int col = pos[1];
        
        //ambil tiap karakter block
        for (int i = 0; i < blockChars.size(); i++) { 
            char blockChar = blockChars.get(i);
            List<List<int[]>> blockVariants = puzzle.blockMap.remove(blockChar); // Hapus blok sebelum rekursi
            if (blockVariants == null) continue;

            //ambil tiap rotasi nya 
            for (int j = 0; j < blockVariants.size(); j++) {
                List<int[]> block = blockVariants.get(j);

                puzzle.countStep++;
                if (isFit(puzzle.board, blockChar, block, row, col)) {
                    //System.out.println(MainTest.countStep);
                    for (int[] posBlock : block) {
                        int newRow = row + posBlock[0];
                        int newCol = col + posBlock[1];
                        puzzle.board[newRow][newCol] = blockChar;
                    }
                    if (solvePuzzle(puzzle)) {
                        return true;
                    }

                    // Backtrack (gagal pasang blok jadi dikosongin lagi)
                    for (int[] posBlock : block) {
                        int newRow = row + posBlock[0];
                        int newCol = col + posBlock[1];
                        puzzle.board[newRow][newCol] = ' ';
                    }
                }
            }
            // Kembalikan blok ke list jika gagal dipasang
            puzzle.blockMap.put(blockChar, blockVariants); 

        }
        return  false;
    }

    public static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
