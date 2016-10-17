package com.adobe.connector.services;

import com.adobe.connector.gateway.Gateway;
import com.adobe.connector.ConnectorRequest;

import java.util.Optional;

public interface GatewayResolver {

    Optional<Gateway> resolve(ConnectorRequest req);

}
