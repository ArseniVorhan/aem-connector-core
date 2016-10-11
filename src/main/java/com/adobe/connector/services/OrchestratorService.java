package com.adobe.connector.services;

import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ConnectorResponse;

public interface OrchestratorService {

    public ConnectorResponse execute(ConnectorRequest req);

}
