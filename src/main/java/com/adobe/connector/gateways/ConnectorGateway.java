package com.adobe.connector.gateways;

public interface ConnectorGateway {

    void executeRequest(final ConnectorRequest req, final ConnectorResponse res) throws Exception;

    String getName();
}
