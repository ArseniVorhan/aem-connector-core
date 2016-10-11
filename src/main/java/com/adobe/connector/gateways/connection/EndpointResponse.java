package com.adobe.connector.gateways.connection;

public interface EndpointResponse<T> {
    public T getResponse();

    boolean isSuccessful();
}
