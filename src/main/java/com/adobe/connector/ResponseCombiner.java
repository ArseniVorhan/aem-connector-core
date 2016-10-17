package com.adobe.connector;

import java.util.Map;

/**
 * Combines all the gateway responses into a single response object
 *
 * @author Benjamin
 */
public interface ResponseCombiner {
    String getName();

    ConnectorResponse combine(Map<String, ConnectorResponse> responses, ConnectorRequest request);
}
