package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Input {
    public static void inputPuzzleData(Puzzle puzzle, File file){
        boolean valid = InputValidator.isValidInput(file);
        if(!valid){
            return;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            int m = fileScanner.nextInt();
            int n = fileScanner.nextInt();
            int pieces = fileScanner.nextInt();
            int objCount = 0;
            int row = 0;

            fileScanner.nextLine();
            String mode = fileScanner.nextLine();
            char[][] board = new char[m][n];

            //buat boardnya ygy
            if ("DEFAULT".equals(mode)) {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        board[i][j] = ' ';
                    }
                }
            }else if ("CUSTOM".equals(mode)) {
                for (int i = 0; i < m; i++) {
                    String line = fileScanner.nextLine();
                    for (int j = 0; j < n; j++) {
                        if (line.charAt(j) == 'X') {
                            board[i][j] = ' ';
                        } else {
                            board[i][j] = '.';
                        }
                    }
                }
            }

            //buat block2nya dalam bentuk koordinat
            Map<Character, List<int[]>> blockList = new LinkedHashMap<>();
            while (fileScanner.hasNextLine() && objCount < pieces) {
                String line = fileScanner.nextLine();
                if (line.isEmpty()) break;

                for(int col=0; col<line.length(); col++){
                    char c = line.charAt(col);
                    if(c != ' '){
                        if(!blockList.containsKey(c)){
                            blockList.put(c, new ArrayList<>());   //masukkan key baru
                            row = 0;                               //reset row
                            //objCount++;
                        }
                        blockList.get(c).add(new int[]{row, col});
                    }
                }
                row++;
            }

            //nah kalau ini tiap block dibuat variasi transformasinya (rotasi,flip atau keduanya)
            Map<Character, List<List<int[]>>> blockMap = new LinkedHashMap<>();
            for (Map.Entry<Character, List<int[]>> entry : blockList.entrySet()) {
                List<List<int[]>> transformations = Puzzle.generateTransformations(entry.getValue());
                blockMap.put(entry.getKey(), transformations);
            }

            puzzle.rows = m;
            puzzle.cols = n;
            puzzle.board = board;
            puzzle.blockMap = blockMap;
        }

        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("File tidak ditemukan. Pastikan path sudah benar.");
        }
    }
}
