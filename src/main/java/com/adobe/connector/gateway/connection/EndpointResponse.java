package com.adobe.connector.gateway.connection;

public interface EndpointResponse<T> {
    public T getResponse();

    boolean isSuccessful();
}
