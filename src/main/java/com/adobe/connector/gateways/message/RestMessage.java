package com.adobe.connector.gateways.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RestMessage extends Message {

    private final static Logger logger = LoggerFactory.getLogger(RestMessage.class);

    private Map<String, String> header;

    public RestMessage(String url, Map<String, String> headers) {
        super(Type.URL, url);
        this.header = headers;
    }

    @Override
    public String getMessageAsString() {
        return url.toString();
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
