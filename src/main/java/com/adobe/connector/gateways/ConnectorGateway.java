package com.adobe.connector.gateways;

/**
 * Generic gateway
 *
 * @author kassa
 */
public interface ConnectorGateway {

    /**
     * Execute the request.
     *
     * @param req
     * @param res
     */
    void executeRequest(final ConnectorRequest req, final ConnectorResponse res) throws Exception;

    /**
     * Unique name of the gateway
     *
     * @return
     */
    String getName();
}
