package com.adobe.connector.services;

import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ConnectorResponse;

import java.util.List;

public interface OrchestratorService {

    ConnectorResponse execute(ConnectorRequest req);

    List<ConnectorResponse> execute(List<ConnectorRequest> reqList);

}
