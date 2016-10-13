package com.adobe.connector.gateways.message;

import java.util.Map;

public class HttpMessage extends Message {

    public enum POST_METHOD {
        FORM, BODY
    }

    private String url;
    private Map<String, String> headers;
    private POST_METHOD postMethod;
    private Map<String, String> formParameters;
    private Body body;

    public HttpMessage(String url) {
        this(url, null, null, null);
    }

    public HttpMessage(String url, String body) {
        this(url, null, body, POST_METHOD.BODY);
    }

    public HttpMessage(String url, Map<String, String> formParameters) {
        this(url, formParameters, null, POST_METHOD.FORM);
    }

    private HttpMessage(String url, Map<String, String> formParameters, String body, POST_METHOD postMethod) {
        super(Type.HTTP);
        this.url = url;
        if (body != null) {
            this.body = new Body(body);
        }
        this.formParameters = formParameters;
        this.postMethod = postMethod;
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
            this.setMediaType(mediaType);
        } else {
            this.body = new Body(null, mediaType);
        }
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }

    public boolean isFormPost() {
        return postMethod == POST_METHOD.FORM;
    }

    public boolean isBodyPost() {
        return postMethod == POST_METHOD.BODY;
    }

    public boolean isPost() {
        return isFormPost() || isBodyPost();
    }

    public boolean isGet() {
        return !isFormPost() && !isBodyPost();
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
