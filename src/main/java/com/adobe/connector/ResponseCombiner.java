package com.adobe.connector;

import java.util.Map;

/**
 * Combines all the gateways responses into a single response object
 *
 * @author Benjamin
 */
public interface ResponseCombiner {
    String getName();

    ConnectorResponse combine(Map<String, ConnectorResponse> responses, ConnectorRequest request);
}
