package org.example.mainframe;

import org.example.config.ConfigPanel;
import org.example.control.ControlPanel;
import org.example.draw.DrawingPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public ConfigPanel configPanel;
    public ControlPanel controlPanel;
    public DrawingPanel canvas;

    public MainFrame() {
        super("Maze Generator - Java GUI");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new DrawingPanel();
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}