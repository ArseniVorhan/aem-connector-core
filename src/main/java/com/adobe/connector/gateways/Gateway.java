package com.adobe.connector.gateways;

import com.adobe.connector.ConnectorResponse;
import com.adobe.connector.gateways.connection.EndpointConnector;
import com.adobe.connector.gateways.connection.EndpointResponse;
import com.adobe.connector.gateways.message.Message;
import com.adobe.connector.utils.PerformanceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Gateway {

    private final static Logger logger = LoggerFactory.getLogger(Gateway.class);

    protected final static PerformanceLogger perfLog = PerformanceLogger.getLogger();

    public abstract String getName();

    protected abstract EndpointConnector getEndpointConnector();

    protected abstract Message getMessage(GatewayRequest gatewayRequest);

    protected abstract ConnectorResponse makeConnectorResponse(EndpointResponse endpointResponse, GatewayRequest gatewayRequest);

    public final GatewayResponse execute(GatewayRequest gatewayRequest) {
        try {
            perfLog.start();

            // First we build the message object
            perfLog.start();
            Message message = getMessage(gatewayRequest);
            perfLog.stop(100001, "Time spent to build the endpoint message", getName());

            // then we execute it
            perfLog.start();
            EndpointResponse endpointResponse = getEndpointConnector().send(message, gatewayRequest);
            perfLog.stop(100002, "Time spent to get endpoint data", getName());

            perfLog.start();
            ConnectorResponse connectorResponse = makeConnectorResponse(endpointResponse, gatewayRequest);
            perfLog.stop(100008, "Time spend to create the ConnectorResponse object", getName());

            perfLog.stop(100007, "Time spent to execute the gateway request", getName());

            return new GatewayResponse(connectorResponse);

        } catch (Throwable e) {
            logger.error("Error when executing the gateway request", e);
        }
        return GatewayResponse.makeFailedResponse();
    }
}
