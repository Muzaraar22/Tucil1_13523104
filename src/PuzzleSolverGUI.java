package src;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class PuzzleSolverGUI extends JFrame {
    private Puzzle puzzle;
    private static File selectedFile;
    private JLabel infoLabel;
    private JPanel boardPanel;
    private JLabel statusLabel;

    public PuzzleSolverGUI() {
        setTitle("Puzzle Solver");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel atas untuk input file
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton loadButton = new JButton("Pilih File");
        loadButton.addActionListener(e -> chooseFile());
        topPanel.add(loadButton);

        JButton saveButton = new JButton("Simpan Solusi");
        saveButton.addActionListener(e -> saveSolution());
        topPanel.add(saveButton);

        // Status Label
        statusLabel = new JLabel("Silakan pilih file puzzle.");
        topPanel.add(statusLabel);

        // Panel board
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(1, 1));
        boardPanel.add(new JLabel("Belum ada puzzle yang dimuat", SwingConstants.CENTER));

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        // Drag and drop file
        setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        selectedFile = files.get(0);
                        loadPuzzle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });

        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File Puzzle");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            loadPuzzle();
        }
    }

    private void loadPuzzle() {
        if (selectedFile == null) {
            return;
        }
        long startTime = System.nanoTime();

        puzzle = new Puzzle();
        Input.inputPuzzleData(puzzle, selectedFile);

        if (puzzle.board == null) {
            statusLabel.setText("Input tidak valdi!");
            return;
        }

        boolean solved = Solver.solvePuzzle(puzzle);
        long endTime = System.nanoTime();
        puzzle.time = (endTime - startTime) / 1_000_000;

        if (!solved) {
            statusLabel.setText("Tidak ada solusi! (" + puzzle.time + " ms, " + puzzle.countStep +  " langkah)");
        } else {
            statusLabel.setText("Solusi ditemukan dalam " + puzzle.time + " ms dan " + puzzle.countStep + " langkah.");
        }

        updateBoard();
    }

    private void updateBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(puzzle.rows, puzzle.cols));

        for (int i = 0; i < puzzle.rows; i++) {
            for (int j = 0; j < puzzle.cols; j++) {
                JLabel cell = new JLabel(" " + puzzle.board[i][j], SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setBackground(getColor(puzzle.board[i][j]));
                boardPanel.add(cell);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private Color getColor(char block) {
        switch (block) {
            case 'A': return Color.RED;
            case 'B': return Color.BLUE;
            case 'C': return Color.GREEN;
            case 'D': return Color.YELLOW;
            case 'E': return Color.ORANGE;
            case 'F': return Color.PINK;
            case 'G': return Color.MAGENTA;
            case 'H': return Color.CYAN;
            case 'I': return Color.LIGHT_GRAY;
            case 'J': return new Color(255, 140, 0);
            case 'K': return new Color(75, 0, 130);
            case 'L': return new Color(139, 69, 19);
            case 'M': return new Color(210, 105, 30);
            case 'N': return new Color(112, 128, 144);
            case 'O': return new Color(255, 215, 0);
            case 'P': return new Color(0, 128, 128);
            case 'Q': return new Color(255, 99, 71);
            case 'R': return new Color(128, 0, 0);
            case 'S': return new Color(0, 255, 127);
            case 'T': return new Color(70, 130, 180);
            case 'U': return new Color(218, 112, 214);
            case 'V': return new Color(46, 139, 87);
            case 'W': return new Color(255, 20, 147);
            case 'X': return new Color(199, 21, 133);
            case 'Y': return new Color(189, 183, 107);
            case 'Z': return new Color(255, 228, 181);
            default: return Color.WHITE;
        }
    }

    private void saveSolution() {
        if (selectedFile == null || puzzle == null || puzzle.board == null) {
            statusLabel.setText("Belum ada solusi mas, pilih file dulu!");
            return;
        }

        File directory = new File("solutions/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = selectedFile.getName().replace(".txt", "_solution.txt");

        try (FileWriter writer = new FileWriter("test/solutions/" + fileName)) {
            for (char[] row : puzzle.board) {
                writer.write(new String(row) + "\n");
            }
            statusLabel.setText("Solusi disimpan: " + fileName);
        } catch (IOException e) {
            statusLabel.setText("Gagal menyimpan solusi!");
        }
    }

    public static void main(String[] args) {
        new PuzzleSolverGUI();
    }
}
