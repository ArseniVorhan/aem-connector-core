package com.adobe.connector.gateways.connection.http;

import com.adobe.connector.gateways.connection.EndpointResponse;

/**
 * Created by stievena on 10/10/16.
 */
public class HttpEndpointResponse implements EndpointResponse<HttpResponse> {

    private HttpResponse response;

    public HttpEndpointResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public HttpResponse getResponse() {
        return response;
    }

    @Override
    public boolean isSuccessful() {
        return response.getStatus() == HttpResponse.SUCCESS;
    }
}
