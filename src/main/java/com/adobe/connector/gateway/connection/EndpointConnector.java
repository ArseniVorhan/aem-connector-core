package com.adobe.connector.gateway.connection;


import com.adobe.connector.gateway.GatewayRequest;
import com.adobe.connector.gateway.message.Message;

public interface EndpointConnector {

    String getName();

    EndpointResponse send(Message message, GatewayRequest gatewayRequest);

}
