package com.adobe.connector.services.impl;

import com.adobe.connector.gateways.Gateway;
import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.services.ExecutionPlanFactory;
import com.adobe.connector.services.GatewayResolver;
import com.adobe.connector.utils.ConnectorUtils;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(immediate = true)
@Service(GatewayResolver.class)
@References({
        @Reference(name = "gatewayMapper", referenceInterface = ExecutionPlanFactory.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC),
        @Reference(name = "gatewayService", referenceInterface = Gateway.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)})
public class GatewayResolverImpl implements GatewayResolver {

    private final static Logger logger = LoggerFactory.getLogger(GatewayResolverImpl.class);

    private final List<ExecutionPlanFactory> gatewayMappers = new ArrayList<>();
    private final Map<String, Gateway> gatewayServices = new HashMap<>();

    protected void bindGatewayMapper(final ExecutionPlanFactory configService, final Map<String, Object> properties) {
        if (configService != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayMappers.add(configService);
            }
        }
    }

    protected void unbindGatewayMapper(final ExecutionPlanFactory configService, final Map<String, Object> properties) {
        if (configService != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayMappers.remove(configService);
            }
        }
    }

    protected void bindGatewayService(final Gateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayServices.put(gateway.getName(), gateway);
            }
        }
    }

    protected void unbindGatewayService(final Gateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayServices.remove(gateway.getName());
            }
        }
    }

    @Override
    public Optional<Gateway> resolve(ConnectorRequest req) {
        return Optional.ofNullable(getGatewayFromConfig(req));
    }

    private Gateway getGatewayFromConfig(ConnectorRequest req) {
        Gateway gateway = null;
        if (this.gatewayMappers.size() > 0 && this.gatewayServices.size() > 0) {
            for (ExecutionPlanFactory config : this.gatewayMappers) {
                if (ConnectorUtils.getClassHierarchy(req).contains(config.getRequest())) {
                    synchronized (this.gatewayServices) {
                        return this.gatewayServices.get(config.getGatewaysName());
                    }
                }
            }
        } else {
            logger.error("No Gateway mapping or Gateway service has been found");
        }

        return gateway;
    }

}
