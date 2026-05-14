package org.example.config;

import org.example.mainframe.MainFrame;
import javax.swing.*;

public class ConfigPanel extends JPanel {
    public final MainFrame frame; // public pentru a fi accesat
    private JLabel label;
    public JSpinner rowsField;    // public pentru a putea fi citit din ControlPanel
    public JSpinner colsField;    // public pentru a putea fi citit din ControlPanel
    private JButton drawBtn;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        label = new JLabel("Maze Size (rows/cols):");
        rowsField = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        colsField = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        drawBtn = new JButton("Draw Grid");

        add(label);
        add(rowsField);
        add(colsField);
        add(drawBtn);

        drawBtn.addActionListener(e -> {
            int r = (int) rowsField.getValue();
            int c = (int) colsField.getValue();
            frame.canvas.initGrid(r, c); // canvas trebuie să fie public în MainFrame
        });
    }
}