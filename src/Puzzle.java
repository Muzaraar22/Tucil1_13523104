package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Puzzle {
    public int rows, cols;
    public int countStep = 0;
    public long time = 0;
    public char[][] board;
    public Map<Character, List<List<int[]>>> blockMap = new LinkedHashMap<>();

    public static List<int[]> rotateBlock(List<int[]> block, int angle) {
        int maxRow = 0, maxCol = 0;
        for (int[] pos : block) {
            maxRow = Math.max(maxRow, pos[0]);
            maxCol = Math.max(maxCol, pos[1]);
        }

        List<int[]> rotatedBlock = new ArrayList<>();
        for (int[] pos : block) {
            int newRow = 0, newCol = 0;
            switch (angle) {
                case 90 -> {
                    newRow = pos[1];
                    newCol = maxRow - pos[0];
                }
                case 180 -> {
                    newRow = maxRow - pos[0];
                    newCol = maxCol - pos[1];
                }
                case 270 -> {
                    newRow = maxCol - pos[1];
                    newCol = pos[0];
                }
                default -> throw new IllegalArgumentException("Sudut rotasi ngaco nih");
            }
            rotatedBlock.add(new int[]{newRow, newCol});
        }
        rotatedBlock = normalize(rotatedBlock);
        return rotatedBlock;
    }

    public static List<int[]> flipBlock(List<int[]> block) {
        int maxRow = 0, maxCol = 0;
        for (int[] pos : block) {
            maxRow = Math.max(maxRow, pos[0]);
            maxCol = Math.max(maxCol, pos[1]);
        }

        List<int[]> flippedBlock = new ArrayList<>();
        for (int[] pos : block) {   //flip horizontal
            int newRow = pos[0];
            int newCol = maxCol - pos[1];
            flippedBlock.add(new int[]{newRow, newCol});
        }
        flippedBlock = normalize(flippedBlock);
        return flippedBlock;
    }

    public static List<int[]> normalize(List<int[]> positions) {
        int minRow = Integer.MAX_VALUE, minCol = Integer.MAX_VALUE;
        for (int[] pos : positions) {
            minRow = Math.min(minRow, pos[0]);
        }
        for (int[] pos : positions) {
            if (pos[0] == minRow)
                minCol = Math.min(minCol, pos[1]);
        }

        List<int[]> normalized = new ArrayList<>();
        for (int[] pos : positions) {
            normalized.add(new int[]{pos[0] - minRow, pos[1] - minCol});
        }

        return normalized;
    }

    public static List<List<int[]>> generateTransformations(List<int[]> baseShape) {
        List<List<int[]>> transformations = new ArrayList<>();
        Set<Set<String>> uniqueShapes = new HashSet<>();
        transformations.add(normalize(baseShape)); // Bentuk awal
        Set<String> baseSet = toSet(normalize(baseShape));
        uniqueShapes.add(baseSet);

        List<List<int[]>> candidates = Arrays.asList(
            rotateBlock(normalize(baseShape), 90),
            rotateBlock(normalize(baseShape), 180),
            rotateBlock(normalize(baseShape), 270),
            flipBlock(normalize(baseShape)),
            rotateBlock(flipBlock(normalize(baseShape)), 90),
            rotateBlock(flipBlock(normalize(baseShape)), 180),
            rotateBlock(flipBlock(normalize(baseShape)), 270)
        );

        for (List<int[]> candidate : candidates) {
            Set<String> candidateSet = toSet(candidate);
            if (!uniqueShapes.contains(candidateSet)) {
                uniqueShapes.add(candidateSet);
                transformations.add(candidate);
            }
        }

        return transformations;
    }

    private static Set<String> toSet(List<int[]> shape) {
        Set<String> set = new HashSet<>();
        for (int[] point : shape) {
            set.add(point[0] + "," + point[1]);
        }
        return set;
    }
}
