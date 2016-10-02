package com.adobe.connector.gateways.http;

/**
 * Created by stievena on 29/09/16.
 */
public class Worker {
    private String url;
    private Processor processor;

    public Worker(String url, Processor processor) {
        this.url = url;
        this.processor = processor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
}
