package com.adobe.connector.gateways.message;

public abstract class Message {

    protected enum Type {
        URL, XML
    }

    private Type type = null;

    protected String url = null;

    protected Message(Type type, String url) {
        this.type = type;
        this.url = url;
    }

    public boolean isSecure() {
        return false;
    }

    public abstract String getMessageAsString();

    public boolean isUrl() {
        return Type.URL.equals(type);
    }

    public boolean isXml() {
        return Type.XML.equals(type);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
