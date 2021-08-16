package com.mby.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.*;

/**
 * class that help implement the UI
 */
public class CacheUnitClientUI extends JPanel {
    private final PropertyChangeSupport pcs;
    private JPanel container;
    protected JTextArea textArea;

    /**
     * constructor
     *
     * @param pcs PropertyChangeSupport object
     */
    public CacheUnitClientUI(PropertyChangeSupport pcs) {
        super();
        this.pcs = pcs;
        reboot();
    }

    public JPanel getPanel() {
        return container;
    }

    /**
     * continue implement the UI
     */
    public void reboot() {

        container = new JPanel();
        container.setBackground(Color.gray);
        container.setLayout(null);
        JButton loadButton = new JButton("Load a Request");
        loadButton.setBounds(280, 40, 200, 50);
        container.add(loadButton);
        JButton statisticButton = new JButton("Show Statistics");
        statisticButton.setBounds(500, 40, 200, 50);
        ButtonListener buttonListener = new ButtonListener();
        textArea = new JTextArea(30, 50);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setEditable(false);
        textArea.setBounds(100, 100, 800, 620);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                textArea.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(100, 100, 800, 620);
        ImageIcon statisticsIcon = new ImageIcon("src/main/resources/statistics.png");
        Image statisticsImage = statisticsIcon.getImage();
        Image newStatisticsImage = statisticsImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        statisticsIcon = new ImageIcon(newStatisticsImage);
        ImageIcon loadIcon = new ImageIcon("src/main/resources/load.png");
        Image loadImage = loadIcon.getImage();
        Image newLoadImage = loadImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        loadIcon = new ImageIcon(newLoadImage);
        loadButton.setIcon(loadIcon);
        statisticButton.setIcon(statisticsIcon);
        container.add(statisticButton);
        container.add(scroll);
        container.add(new JSeparator(), BorderLayout.PAGE_END);
        loadButton.addActionListener(buttonListener);
        statisticButton.addActionListener(buttonListener);
    }

    /**
     * a class that implement the button clicks reaction
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Load a Request": {
                    String filePath;
                    JFileChooser chooser = new JFileChooser(new File("src\\main\\resources").getAbsolutePath());    //Choose Json file
                    int status = chooser.showOpenDialog(null);
                    if (status != JFileChooser.APPROVE_OPTION) {
                        textArea.setText("No file selected");
                    } else {
                        File file = chooser.getSelectedFile();
                        filePath = file.getAbsolutePath();
                        try {
                            Scanner scan = new Scanner(new FileReader(filePath));
                            StringBuilder json = new StringBuilder(scan.next());
                            while (scan.hasNext()) {
                                json.append(scan.next());
                            }
                            pcs.firePropertyChange(null, null, json.toString());
                            scan.close();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                }
                case "Show Statistics": {
                    pcs.firePropertyChange(null, null, "statistics");
                    break;
                }
                case "":
                    textArea.setText("");
            }
        }
    }
}
