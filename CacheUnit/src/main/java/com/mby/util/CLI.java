package com.mby.util;

import java.io.*;
import java.beans.*;
import java.util.Scanner;

/**
 * This class will be responsible for the interface designed
 * to be used as a communication between the client and the server
 * in order to activate the server and stop its operation if necessary
 */
public class CLI implements Runnable {

    private final Scanner in;
    private final OutputStream out;
    private final PropertyChangeSupport PCS;

    /**
     * Constructor
     *
     * @param in InputStream
     * @param out OutputStream
     */
    public CLI( InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = out;
        this.PCS = new PropertyChangeSupport(this);
    }

    /**
     * A function used to add a new listener to the PCL
     * @param pcl PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        PCS.addPropertyChangeListener(pcl);
    }

    /**
     * A function used to remove a new listener from the PCL
     * @param pcl PropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        PCS.removePropertyChangeListener(pcl);
    }

    /**
     * A function used to write to the outputStream the given string
     * @param string the string to write
     */
    public void write(String string) {
        try {
            out.write((string + '\n').getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String currentCommand;
        while (true) {
            write("Please enter your command");
            currentCommand = in.nextLine();
            if (currentCommand.equals("start") || currentCommand.equals("stop")) {
                PCS.firePropertyChange("currentCommand", null, currentCommand);
            } else {
                if (currentCommand.equals("exit")) {
                    PCS.firePropertyChange("currentCommand", null, "stop");
                    System.exit(0);
                    break;
                } else {
                    write("Non valid command");
                }
            }
        }
    }
}