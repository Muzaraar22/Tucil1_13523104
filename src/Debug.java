package src;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Debug {
    public static void printBlockVariant(Map<Character, List<List<int[]>>> shapeMap){
        for (Map.Entry<Character, List<List<int[]>>> entry : shapeMap.entrySet()) {
            System.out.println("Huruf '" + entry.getKey() + "' memiliki bentuk:");
            int index = 1;
            for (List<int[]> shape : entry.getValue()) {
                System.out.println("Bentuk " + index + ": " + coordinatesToString(shape));
                index++;
            }
        }
    }

    private static String coordinatesToString(List<int[]> coordinates) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int[] coord : coordinates) {
            sb.append("(").append(coord[0]).append(",").append(coord[1]).append("), ");
        }
        if (!coordinates.isEmpty()) sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    //Test piece
    public static void printBlockList(Map<Character, List<int[]>> puzzle){
        for (Map.Entry<Character, List<int[]>> entry : puzzle.entrySet()) {
            List<int[]> positions = entry.getValue();
            System.out.print(entry.getKey() + ": ");
            for (int[] pos : positions) {
                System.out.print(Arrays.toString(pos) + " ");
            }
            System.out.println();
        }
    }
    public static void printBlock(List<int[]> block){
        for (int[] pos : block) {
            System.out.println(Arrays.toString(pos));
        }
    }
}
