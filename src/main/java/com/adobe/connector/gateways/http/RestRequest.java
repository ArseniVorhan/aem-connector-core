package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorRequest;

/**
 * Created by stievena on 29/09/16.
 */
public abstract class RestRequest implements ConnectorRequest {
    protected abstract String[] getParameters();
}
