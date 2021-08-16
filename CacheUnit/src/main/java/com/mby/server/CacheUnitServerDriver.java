package com.mby.server;
import com.mby.util.CLI;

public class CacheUnitServerDriver {
    /**
     * constructor
     */
    public CacheUnitServerDriver() {

    }

    /**
     * main function, prepares the program to run the server
     * @param args the program args
     */
    public static void main(java.lang.String[] args) {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();
    }
}