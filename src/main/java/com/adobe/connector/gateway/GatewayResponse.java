package com.adobe.connector.gateway;

import com.adobe.connector.ConnectorResponse;

/**
 * Created by stievena on 09/10/16.
 */
public class GatewayResponse {

    private final static GatewayResponse FAILED_RESPONSE = new GatewayResponse(null);

    private ConnectorResponse connectorResponse;

    public GatewayResponse(ConnectorResponse connectorResponse) {
        this.connectorResponse = connectorResponse;
    }

    public ConnectorResponse getConnectorResponse() {
        return connectorResponse;
    }

    public static GatewayResponse makeFailedResponse() {
        return FAILED_RESPONSE;
    }
}
