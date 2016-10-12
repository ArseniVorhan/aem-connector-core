package com.adobe.connector.gateways.message;

public abstract class Message {

    protected enum Type {
        HTTP, SQL
    }

    private Type type = null;

    protected Message(Type type) {
        this.type = type;
    }

    public boolean isHttp() {
        return Type.HTTP.equals(type);
    }

    public boolean isSql() {
        return Type.SQL.equals(type);
    }

}
