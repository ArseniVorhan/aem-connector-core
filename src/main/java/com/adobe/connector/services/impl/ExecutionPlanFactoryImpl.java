package com.adobe.connector.services.impl;

import com.adobe.connector.services.ExecutionPlanFactory;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component(immediate = true, label = "Adobe Connector Gateway Mapper", description = "Map a Gateway to a ConnectorRequest", metatype = true, configurationFactory = true)
@Service(ExecutionPlanFactory.class)
public class ExecutionPlanFactoryImpl implements ExecutionPlanFactory {

    private final static Logger logger = LoggerFactory.getLogger(ExecutionPlanFactoryImpl.class);

    @Property(label = "Request", description = "Provide the fully qualified name of the request.")
    private static final String REQUEST = "request";

    @Property(label = "Gateways' name", description = "Provide the list of gateways' name that should resolve the request.")
    private static final String GATEWAY_NAME = "gateway.name";

    @Property(label = "Response combiner", description = "Provide the name of the response combiner. Leave empty if not combiner is needed.", unbounded = PropertyUnbounded.ARRAY)
    private static final String RESPONSE_COMBINER_NAME = "combiner.name";

    private String[] gatewaysName;
    private String request;
    private String responseCombinerName;

    @Activate
    protected void activate(final Map<String, Object> config) {
        this.gatewaysName = PropertiesUtil.toStringArray(config.get(GATEWAY_NAME));
        this.request = PropertiesUtil.toString(config.get(REQUEST), "");
        this.responseCombinerName = PropertiesUtil.toString(config.get(RESPONSE_COMBINER_NAME), "");
        logger.info("New gateway mapping [" + gatewaysName + " = " + request + "]");
    }

    public String[] getGatewaysName() {
        return this.gatewaysName;
    }

    public String getRequest() {
        return this.request;
    }

    @Override
    public String getResponseCombiner() {
        return this.responseCombinerName;
    }

}
