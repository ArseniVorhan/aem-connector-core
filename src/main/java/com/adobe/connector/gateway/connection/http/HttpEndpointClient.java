package com.adobe.connector.gateway.connection.http;

import com.adobe.connector.gateway.message.Message;

/**
 * Created by stievena on 10/10/16.
 */
public interface HttpEndpointClient {
    HttpResponse getHttpResponse(Message message);
}
