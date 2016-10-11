package com.adobe.connector.gateways.connection.http;

import com.adobe.connector.gateways.message.Message;

/**
 * Created by stievena on 10/10/16.
 */
public interface HttpEndpointClient {
    HttpResponse getHttpResponse(Message message);
}
