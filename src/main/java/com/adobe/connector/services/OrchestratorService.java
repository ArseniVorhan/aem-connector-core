package com.adobe.connector.services;

import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;

/**
 * Orchestrator which execute all requests.
 * 
 * @author kassa
 *
 */
public interface OrchestratorService {
    /**
     * execute all type of requests.
     * 
     * @param req
     * @param resp
     * @throws Exception
     */
    public void execute(ConnectorRequest req, ConnectorResponse res) throws Exception;

}
