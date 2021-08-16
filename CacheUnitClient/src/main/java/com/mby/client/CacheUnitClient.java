package com.mby.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A class designed to send client requests to the server,
 * receive the response from the server and return the response
 */
public class CacheUnitClient {

    public static final String LOCAL_HOST = "localhost";
    public static final Integer PORT = 12345;

    /**
     * constructor
     */
    public CacheUnitClient() {
    }

    /**
     * Send Request From Client to Server
     *
     * @param request - the clients request
     * @return response from the Server
     */
    public String send(String request) {
        String response = null;

        try {
            InetAddress address = InetAddress.getByName(LOCAL_HOST);
            Socket socket = new Socket(address, PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream stream = new DataInputStream(socket.getInputStream());

            if (request.equals("statistics")) {
                out.writeUTF(request);
                out.flush();
                response = (String) stream.readUTF();
            } else {
                out.writeUTF(request);
                out.flush();
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String content = "";
                do {
                    content = in.readUTF();
                    sb.append(content).append("\n");
                } while (in.available() != 0);
                response = sb.toString();
            }
            socket.close();
            out.close();
            stream.close();
            if (response.equals("true\n"))
            return "Succeeded\n";
            if (response.equals("false\n"))
                return "Failed\n";
            return response;

        } catch (IOException e) {
            return "No connection to server";
        }
    }
}