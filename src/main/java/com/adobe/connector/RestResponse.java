package com.adobe.connector;

import java.util.List;

/**
 * Created by stievena on 17/10/16.
 */
public class RestResponse<T> extends ConnectorResponse<T> {
    private int status;
    private String message;
    private List<T> results;

    public RestResponse(List<T> results, int status, String message) {
        this.status = status;
        this.message = message;
        this.results = results;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public List<T> getResults() {
        return results;
    }
}
