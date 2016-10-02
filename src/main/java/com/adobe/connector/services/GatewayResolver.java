package com.adobe.connector.services;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;

import java.util.Optional;

public interface GatewayResolver {

    Optional<ConnectorGateway> resolve(ConnectorRequest req);

}
