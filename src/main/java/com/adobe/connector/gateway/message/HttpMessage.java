package com.adobe.connector.gateway.message;

import java.util.Map;

public class HttpMessage extends Message {

    public static enum HTTP_METHOD {
        GET, POST, PUT, DELETE, PATCH
    }

    private String url;
    private Map<String, String> headers;
    private HTTP_METHOD httpMethod;
    private Map<String, String> formParameters;
    private Body body;

    public HttpMessage(String url) {
        this(url, null, null, HTTP_METHOD.GET);
    }

    public HttpMessage(String url, String body) {
        this(url, null, body, HTTP_METHOD.POST);
    }

    public HttpMessage(String url, String body, HTTP_METHOD httpMethod) {
        this(url, null, body, httpMethod);
    }

    public HttpMessage(String url, Map<String, String> formParameters) {
        this(url, formParameters, null, HTTP_METHOD.POST);
    }

    private HttpMessage(String url, Map<String, String> formParameters, String body, HTTP_METHOD httpMethod) {
        super(Type.HTTP);
        this.url = url;
        if (body != null) {
            this.body = new Body(body);
        }
        this.formParameters = formParameters;
        this.httpMethod = httpMethod;
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
        if (this.body != null) {
            return this.body.getBody();
        }
        return null;
    }

    public String getMediaType() {
        if (this.body != null) {
            return this.body.getMediaType();
        }
        return null;
    }

    public void setMediaType(String mediaType) {
        if (this.body != null) {
            this.body.setMediaType(mediaType);
        } else {
            this.body = new Body(null, mediaType);
        }
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }


    public HTTP_METHOD getHttpMethod() {
        return httpMethod;
    }

    public boolean isPost() {
        return httpMethod == HTTP_METHOD.POST;
    }

    public boolean isGet() {
        return httpMethod == HTTP_METHOD.GET;
    }

    public boolean isPut() {
        return httpMethod == HTTP_METHOD.PUT;
    }

    public boolean isDelete() {
        return httpMethod == HTTP_METHOD.DELETE;
    }

    private class Body {

        private static final String DEFAULT_MEDIA_TYPE = "application/json";

        private String mediaType;
        private String body;

        public Body(String body) {
            setMediaType(DEFAULT_MEDIA_TYPE);
            this.body = body;
        }

        public Body(String mediaType, String body) {
            setMediaType(mediaType);
            setBody(body);
        }

        public String getMediaType() {
            return mediaType;
        }

        public String getBody() {
            return body;
        }

        public void setMediaType(String mediaType) {
            if (mediaType.contains("charset")) {
                this.mediaType = mediaType;
            } else {
                this.mediaType = mediaType + "; charset=utf-8";
            }
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
