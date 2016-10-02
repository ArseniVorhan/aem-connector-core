package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;

import java.io.InputStream;

/**
 * Created by stievena on 29/09/16.
 */
public interface Processor {
    String getName();

    void process(InputStream inputStream, ConnectorRequest connectorRequest, ConnectorResponse connectorResponse);
}
