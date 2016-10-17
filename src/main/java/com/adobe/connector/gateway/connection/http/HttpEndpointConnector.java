package com.adobe.connector.gateway.connection.http;

import com.adobe.connector.gateway.GatewayRequest;
import com.adobe.connector.gateway.connection.EndpointConnector;
import com.adobe.connector.gateway.connection.EndpointResponse;
import com.adobe.connector.gateway.message.Message;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

/**
 * Created by stievena on 10/10/16.
 */
@Component(immediate = true)
@Service(value = EndpointConnector.class)
public class HttpEndpointConnector implements EndpointConnector {

    @Reference
    private HttpEndpointClient httpEndpointClient;

    @Override
    public String getName() {
        return "HttpEndpointConnector";
    }

    @Override
    public EndpointResponse send(Message message, GatewayRequest gatewayRequest) {
        return new HttpEndpointResponse(httpEndpointClient.getHttpResponse(message));
    }
}
