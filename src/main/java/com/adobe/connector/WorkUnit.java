package com.adobe.connector;


import com.adobe.connector.gateways.GatewayRequest;
import com.adobe.connector.gateways.GatewayResponse;

public class WorkUnit {

    private GatewayRequest gatewayRequest;
    private GatewayResponse gatewayResponse;
    private String gateway;

    public WorkUnit(GatewayRequest gatewayRequest, String gateway) {
        this.gatewayRequest = gatewayRequest;
        this.gateway = gateway;
    }

    public String getGateway() {
        return gateway;
    }

    public GatewayRequest getGatewayRequest() {
        return gatewayRequest;
    }

    public GatewayResponse getGatewayResponse() {
        return gatewayResponse;
    }

    public void setGatewayResponse(GatewayResponse gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

}
