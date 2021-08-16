package com.mby.server;

import com.mby.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class that serves as a server for managing communication with clients
 */
public class Server implements Runnable, PropertyChangeListener {
    private String command;
    private final int port;
    private ServerSocket serverSocket;
    private Thread thread;
    private final CacheUnitController<String> controller;
    private boolean serverUp;

    /**
     * constructor
     */
    public Server() {
        this.command = null;
        this.port = 12345;//the server port
        this.controller = new CacheUnitController<>();
        this.serverUp = false;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.command = (String) evt.getNewValue();
        if (this.command.equals("start")) {
            try {
                if (!serverUp) {
                    this.serverSocket = new ServerSocket(this.port);
                    this.thread = new Thread(this);
                    this.thread.start();
                    this.serverUp = true;
                }
                System.out.println("Staring server...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.controller.shutDown();
                this.serverUp = false;
                if (this.thread != null) {
                    this.thread.stop();
                    this.serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        while (serverUp) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                HandleRequest<String> handleClientRequest = new HandleRequest(clientSocket, controller);
                service.execute(handleClientRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
