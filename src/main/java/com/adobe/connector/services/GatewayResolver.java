package com.adobe.connector.services;

import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorGateway;

import java.util.Optional;

/**
 * Gateway resolver.
 *
 * @author kassa
 */
public interface GatewayResolver {

    /**
     * Return gateway based on the request.
     *
     * @param req
     * @return
     */
    Optional<ConnectorGateway> resolve(ConnectorRequest req);

    void releaseGateway(ConnectorGateway gateway);

}
