package com.mby.server;

import java.io.Serializable;
import java.util.Map;

/**
 *A class used to implement a data structure designed
 * to store the command sent from the client
 * @param <T> the body type
 */
public class Request<T> implements Serializable {
    private Map<String,String> headers;
    private T body;


    /**
     *constructor
     * @param header the requests' header - the requests' action
     * @param body the requests' body- the data
     */
    public Request(Map<String, String> header, T body) {
        this.headers = header;
        this.body = body;
    }

    /**
     *get the header
     * @return the requests' header
     */
    public Map<String, String> getHeaders() {
        return headers;
    }


    /**
     * set the header
     * @param header the new header
     */
    public void setHeaders(Map<String, String> header) {
        this.headers = header;
    }

    /**
     * set the body
     * @param body the new body
     */
    public void setBody(T body) {
        this.body = body;
    }

    /**
     *
     * @return the requests' bode
     */
    public T getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + headers +
                ", body=" + body +
                '}';
    }
}
