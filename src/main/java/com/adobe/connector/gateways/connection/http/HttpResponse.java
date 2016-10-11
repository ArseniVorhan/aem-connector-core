package com.adobe.connector.gateways.connection.http;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by stievena on 10/10/16.
 */
public class HttpResponse {
    public final static int SUCCESS = HttpServletResponse.SC_OK;

    private byte[] data;
    private int status;
    private String message;

    public HttpResponse(byte[] data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "data=" + Arrays.toString(data) +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
