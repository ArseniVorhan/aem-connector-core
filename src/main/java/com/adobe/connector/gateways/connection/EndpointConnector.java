package com.adobe.connector.gateways.connection;


import com.adobe.connector.gateways.GatewayRequest;
import com.adobe.connector.gateways.message.Message;

public interface EndpointConnector {

    String getName();

    EndpointResponse send(Message message, GatewayRequest gatewayRequest);

}
