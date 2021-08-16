package com.mby.view;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

/**
 *class that implement the UI
 */
public class CacheUnitView {

    private JFrame frame;
    private CacheUnitClientUI ui;
    private PropertyChangeSupport pcs;

    /**
     *constructor
     */
    public CacheUnitView() {
        pcs = new PropertyChangeSupport(this);
        ui = new CacheUnitClientUI(pcs);
        frame = new JFrame("CacheUnitUI");
    }


    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    /**
     * create the UI
     */
    public void start() {
        frame.add(ui.getPanel());
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     *write to the client screen
     * @param t the content to write to the client screen
     * @param <T> the  content type
     */
    public <T> void updateUIData(T t) {
        ui.textArea.setText((String) t);
    }
}
