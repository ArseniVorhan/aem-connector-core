package com.adobe.connector.services;

/**
 * Created by stievena on 28/09/16.
 */
public interface ExecutionPlanFactory {
    String[] getGatewaysName();

    String getRequest();

    String getResponseCombiner();
}
