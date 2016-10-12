package com.adobe.connector.gateways.message;

import java.util.Map;

public class HttpMessage extends Message {

    public enum HTTP_METHOD {
        GET, POST
    }

    private String url;
    private Map<String, String> headers;
    private Map<String, String> formParameters;
    private String body;
    private HTTP_METHOD method;

    public HttpMessage(String url) {
        this(url, null, null, null, HTTP_METHOD.GET);
    }

    public HttpMessage(String url, String body) {
        this(url, null, null, body, HTTP_METHOD.POST);
    }

    private HttpMessage(String url, Map<String, String> formParameters, Map<String, String> headers, String body, HTTP_METHOD method) {
        super(Type.HTTP);
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HTTP_METHOD getMethod() {
        return method;
    }

    public void setMethod(HTTP_METHOD method) {
        this.method = method;
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }

    public void setFormParameters(Map<String, String> formParameters) {
        this.formParameters = formParameters;
    }

    public boolean isGet() {
        return method == HTTP_METHOD.GET;
    }

    public boolean isPost() {
        return method == HTTP_METHOD.POST;
    }

}
