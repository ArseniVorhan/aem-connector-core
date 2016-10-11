package com.adobe.connector.gateways;

import com.adobe.connector.ConnectorRequest;

/**
 * Created by stievena on 09/10/16.
 */
public class GatewayRequest {
    private ConnectorRequest connectorRequest;

    public GatewayRequest(ConnectorRequest connectorRequest) {
        this.connectorRequest = connectorRequest;
    }

    public ConnectorRequest getConnectorRequest() {
        return connectorRequest;
    }
}
