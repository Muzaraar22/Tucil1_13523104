package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JOptionPane;

public class InputValidator {
    public static boolean isValidInput(File file){ 
        try {
            Scanner fileScanner = new Scanner(file);

            //cek input 3 angka pertama
            if (!fileScanner.hasNextInt()) {
                System.out.println("Error: Input pertama bukan angka!");
                JOptionPane.showMessageDialog(null, "Error: Input pertama bukan angka!", "Hasil", JOptionPane.ERROR_MESSAGE);

                return false;
            }
            int m = fileScanner.nextInt();

            if (!fileScanner.hasNextInt()) {
                System.out.println("Error: Input kedua bukan angka!");
                JOptionPane.showMessageDialog(null, "Error: Input kedua bukan angka!", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            int n = fileScanner.nextInt();

            if (!fileScanner.hasNextInt()) {
                System.out.println("Error: Input ketiga bukan angka!");
                JOptionPane.showMessageDialog(null, "Error: Input ketiga bukan angka!", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            int blocks = fileScanner.nextInt();

            fileScanner.nextLine();
            String mode = fileScanner.nextLine();
            //System.out.println(mode);


            if (!"DEFAULT".equals(mode) && !"CUSTOM".equals(mode)) {
                System.out.println("Error: Mode tidak sesuai!");
                JOptionPane.showMessageDialog(null, "Error: Mode tidak sesuai!", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int slotCount = m*n;
            if ("CUSTOM".equals(mode)) {
                int boardRow = 0;
                slotCount = 0;
                while (fileScanner.hasNextLine() && boardRow < m) {
                    String line = fileScanner.nextLine();
                    if (line.length() != n) {
                        System.out.println("Error: Panjang board tidak sesuai!");
                        JOptionPane.showMessageDialog(null, "Error: Panjang board tidak sesuai!", "Hasil", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == 'X') {
                            slotCount++;
                        }
                    }
                    boardRow++;
                }
            }

            //cek input block
            int row = 0;
            int pieces = 0;
            int blockCount = 0;
            Map<Character, List<int[]>> blockList = new LinkedHashMap<>();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.isEmpty()) break;

                Set<Character> charSet = new HashSet<>();
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);

                    if (c != ' ') {
                        //cek jumlah pieces/karakter
                        pieces++;

                        //1 baris ada huruf berbeda
                        charSet.add(c);
                        if (charSet.size() > 1) {
                            System.out.println("1 baris ada huruf berbeda");
                            JOptionPane.showMessageDialog(null, "1 baris ada huruf berbeda", "Hasil", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }

                        //cek posisi block apakah ada yang tidak menyatu atau terpisah
                        if(!blockList.containsKey(c)){
                            blockList.put(c, new ArrayList<>());
                            blockCount++;
                            //System.out.println("Block baru: " + c + " " + blockCount);
                        }
                        blockList.get(c).add(new int[]{row, col});
                    }
                }
                row++;
            }
            

            if (!isConnectedBlock(blockList)) {
                System.out.println("Block tidak terhubung");
                JOptionPane.showMessageDialog(null, "Block tidak terhubung", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if ((pieces != slotCount)) {
                System.out.println("Jumlah pieces tidak sesuai ya");
                JOptionPane.showMessageDialog(null, "Jumlah pieces tidak sesuai", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (isLargerThanBoard(blockList, m, n)) {
                System.out.println("Block lebih besar dari board");
                JOptionPane.showMessageDialog(null, "Block lebih besar dari board", "Hasil", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (blockCount != blocks) {
                System.out.println("Jumlah block tidak sesuai");
                return false;
            }

            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean isConnectedBlock(Map<Character, List<int[]>> blockList){
        for (Map.Entry<Character, List<int[]>> entry : blockList.entrySet()) {
            List<int[]> block = entry.getValue();
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            Queue<int[]> queue = new LinkedList<>();

            Set<String> visited = new HashSet<>();
            queue.add(block.get(0));
            visited.add(block.get(0)[0] + "," + block.get(0)[1]);

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                for (int[] dir : directions) {
                    int newRow = current[0] + dir[0];
                    int newCol = current[1] + dir[1];

                    String newPosStr = newRow + "," + newCol;
                    if (block.stream().anyMatch(b -> b[0] == newRow && b[1] == newCol) &&
                        !visited.contains(newPosStr)) {
                        queue.add(new int[]{newRow, newCol});
                        visited.add(newPosStr);
                    }
                }
            }
            
            if (visited.size() != block.size()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLargerThanBoard(Map<Character, List<int[]>> blockList, int m, int n){
        for (Map.Entry<Character, List<int[]>> entry : blockList.entrySet()) {
            List<int[]> block = entry.getValue();
            int maxRow = 0, maxCol = 0, minRow = 100, minCol = 100;
            for (int[] pos : block) {
                maxRow = Math.max(maxRow, pos[0]);
                minRow = Math.min(minRow, pos[0]);
                maxCol = Math.max(maxCol, pos[1]);
                minCol = Math.min(minCol, pos[1]);
            }

            int length1 = maxRow - minRow + 1;
            int length2 = maxCol - minCol + 1;
            if (length1 > m || length2 > n) {
                return true;
            }
        }
        return false;
    }
}
