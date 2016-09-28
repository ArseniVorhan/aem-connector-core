package com.adobe.connector.services.impl;

import com.adobe.connector.services.GatewayFactoryService;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component(immediate = true, label = "Adobe Connector Gateway Mapper", description = "Map a ConnectorGateway to a ConnectorRequest", metatype = true)
@Service(GatewayFactoryService.class)
public class GatewayFactoryServiceImpl implements GatewayFactoryService {

    private final static Logger logger = LoggerFactory.getLogger(GatewayFactoryServiceImpl.class);

    @Property(label = "Gateway name", description = "Provide a unique name for this gateway")
    private static final String GATEWAY_NAME = "name";

    @Property(label = "Allowed request type", description = "Provide the qualified name of the request handled by the gateway. Must inherit from com.adobe.connector.gateways.ConnectorRequest.")
    private static final String GATEWAY_REQUEST_ALLOWED = "request.allowed";

    private String gatewayName;
    private String gatewayRequestAllowed;

    @Activate
    protected void activate(final Map<String, Object> config) {
        this.gatewayName = PropertiesUtil.toString(config.get(GATEWAY_NAME), "");
        this.gatewayRequestAllowed = PropertiesUtil.toString(config.get(GATEWAY_REQUEST_ALLOWED), "");
        logger.info("Activating gateway [" + gatewayName + " = " + gatewayRequestAllowed + "]");
    }

    public String getGatewayName() {
        return this.gatewayName;
    }

    public String getGatewayRequestAllowed() {
        return this.gatewayRequestAllowed;
    }

}
