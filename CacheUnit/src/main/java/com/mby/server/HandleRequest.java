package com.mby.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mby.dm.DataModel;
import com.mby.services.CacheUnitController;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;

/**
 * The role of this class is to get the request (Socket) from the server,
 * read from it the information that will always be a Request type object
 * that comes in json structure with the help of the relevant classes packed in jar.gson,
 * read from the Request Header the action field
 * and pass it For the relevant method in CacheUnitController
 *
 * @param <T>
 */
public class HandleRequest<T> implements Runnable {
    private final Socket requestSocket;
    private final CacheUnitController controller;
    private static Long amountRequest = (long) 0;
    private static final String statistics = "statistics";


    /**
     * constructor
     *
     * @param s          the socket (the request)
     * @param controller CacheUnitController object
     */
    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.requestSocket = s;
        this.controller = controller;
    }

    /**
     * reads the request from the client and handle it according to the type of the request
     */
    @Override
    public void run() {
        DataOutputStream writer = null;
        DataInputStream reader = null;

        try {
            writer = new DataOutputStream(requestSocket.getOutputStream());
            reader = new DataInputStream(new BufferedInputStream(requestSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String content;
            assert reader != null;
            content = reader.readUTF();
            if (statistics.equals(content)) {
                handleStatistics(writer);
            } else {
                requestHandler(writer, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param writer DataOutputStream to write to the client
     * @throws IOException in case the writeUTF failed
     */
    private void handleStatistics(DataOutputStream writer) throws IOException {
        String content = "Algo: " + controller.getAlgo() + "\n" +
                "Capacity: " + controller.getCapacity() + "\n" +
                "Request: " + amountRequest + "\n" +
                "DataModels: " + controller.getDataModels() + "\n" +
                "DataModelsSwaps: " + controller.getDataModelsSwaps() + "\n";
        writer.writeUTF(content);
        writer.flush();
    }

    /**
     * @param writer  DataOutputStream to write to the client
     * @param content the request from the client
     * @throws Exception in case the writeUTF failed
     */
    private void requestHandler(DataOutputStream writer, String content) throws Exception {
        amountRequest++;
        boolean respond = false;
        DataModel<T>[] getRespond = null;
        Request<DataModel<T>[]> request;
        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        request = new Gson().fromJson(content, ref);
        String action = request.getHeaders().get("action");
        switch (action) {
            case "UPDATE":
                respond = controller.update(request.getBody());
                writer.writeUTF(String.valueOf(respond));
                writer.flush();
                break;
            case "DELETE":
                respond = controller.delete(request.getBody());
                writer.writeUTF(String.valueOf(respond));
                writer.flush();
                break;
            case "GET":
                getRespond = controller.get(request.getBody());
                StringBuilder sb = new StringBuilder();
                for (DataModel elem : getRespond) {

                    if (elem != null) {
                        sb.append(elem.toString()).append("\n");
                    } else {
                        sb.append("this element doesn't exist in the cache unit").append("\n");
                    }
                }
                writer.writeUTF(sb.toString());
                writer.flush();
                break;
        }
    }


}
