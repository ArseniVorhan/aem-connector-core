package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorResponse;

import java.io.Serializable;

/**
 * Created by stievena on 29/09/16.
 */
public abstract class RestResponse implements ConnectorResponse {
    public abstract <T extends Serializable> T getModel();
}
