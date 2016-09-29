package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;
import okhttp3.Response;

/**
 * Created by stievena on 29/09/16.
 */
public interface Processor {
    String getName();

    void process(Response httpResponse, ConnectorRequest connectorRequest, ConnectorResponse connectorResponse);
}
