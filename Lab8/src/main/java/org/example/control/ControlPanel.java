package org.example.control;

import org.example.mainframe.MainFrame;
import javax.swing.*;

public class ControlPanel extends JPanel {
    public final MainFrame frame;
    private JButton createBtn = new JButton("Create");
    private JButton resetBtn = new JButton("Reset");
    private JButton validateBtn = new JButton("Validate");
    private JButton exportBtn = new JButton("Export PNG");
    private JButton saveBtn = new JButton("Save");
    private JButton loadBtn = new JButton("Load");
    private JButton exitBtn = new JButton("Exit");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        add(createBtn);
        add(resetBtn);
        add(validateBtn);
        add(exportBtn);
        add(saveBtn);
        add(loadBtn);
        add(exitBtn);

        createBtn.addActionListener(e -> frame.canvas.randomizeWalls());

        resetBtn.addActionListener(e -> {
            // citesc valorile din panoul de configurare
            int r = (int) frame.configPanel.rowsField.getValue();
            int c = (int) frame.configPanel.colsField.getValue();
            frame.canvas.initGrid(r, c);
        });

        validateBtn.addActionListener(e -> {
            boolean isTraversable = frame.canvas.validateMaze();
            String message = isTraversable ? "Labirintul ESTE rezolvabil!" : "Labirintul NU se poate traversa!";
            JOptionPane.showMessageDialog(this, message);
        });

        exportBtn.addActionListener(e -> frame.canvas.exportToPNG());

        saveBtn.addActionListener(e -> frame.canvas.saveMaze());

        loadBtn.addActionListener(e -> frame.canvas.loadMaze());

        exitBtn.addActionListener(e -> System.exit(0));
    }
}