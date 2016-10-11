package com.adobe.connector.services;

import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ExecutionPlan;

/**
 * Created by stievena on 09/10/16.
 */
public interface ExecutionPlanBuilder {
    ExecutionPlan buildExecutionPlan(ConnectorRequest connectorRequest);
}
