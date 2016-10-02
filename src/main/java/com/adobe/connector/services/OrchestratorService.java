package com.adobe.connector.services;

import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;

public interface OrchestratorService {

    public void execute(ConnectorRequest req, ConnectorResponse res);

}
