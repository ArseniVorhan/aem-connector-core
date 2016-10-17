package com.adobe.connector;

import java.util.HashMap;
import java.util.Map;

public class RestRequest extends ConnectorRequest {

    protected String[] parameters;
    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, String> formAttributes = new HashMap<>();
    protected Object body;

    public RestRequest(String... parameters) {
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return this.parameters;
    }

    public Map<String, String> getFormAttributes() {
        return formAttributes;
    }

    public void addFormAttribute(String key, String value) {
        formAttributes.put(key, value);
    }

    public String getFormAttribute(String key) {
        return formAttributes.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
