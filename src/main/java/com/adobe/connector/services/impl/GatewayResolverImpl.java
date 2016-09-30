package com.adobe.connector.services.impl;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.services.GatewayFactoryService;
import com.adobe.connector.services.GatewayResolver;
import com.adobe.connector.utils.ConnectorUtils;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(immediate = true)
@Service(GatewayResolver.class)
@References({
        @Reference(name = "gatewayMapper", referenceInterface = GatewayFactoryService.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC),
        @Reference(name = "gatewayService", referenceInterface = ConnectorGateway.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)})
public class GatewayResolverImpl implements GatewayResolver {

    private final static Logger logger = LoggerFactory.getLogger(GatewayResolverImpl.class);

    private final List<GatewayFactoryService> gatewayMappers = new ArrayList<>();
    private final Map<String, ConnectorGateway> gatewayServices = new HashMap<>();

    protected void bindGatewayMapper(final GatewayFactoryService configService, final Map<String, Object> properties) {
        if (configService != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayMappers.add(configService);
            }
        }
    }

    protected void unbindGatewayMapper(final GatewayFactoryService configService, final Map<String, Object> properties) {
        if (configService != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayMappers.remove(configService);
            }
        }
    }

    protected void bindGatewayService(final ConnectorGateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayServices.put(gateway.getName(), gateway);
            }
        }
    }

    protected void unbindGatewayService(final ConnectorGateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayServices.remove(gateway.getName());
            }
        }
    }

    @Override
    public Optional<ConnectorGateway> resolve(ConnectorRequest req) {
        return Optional.ofNullable(getGatewayFromConfig(req));
    }

    private ConnectorGateway getGatewayFromConfig(ConnectorRequest req) {
        ConnectorGateway connectorGateway = null;
        if (this.gatewayMappers.size() > 0 && this.gatewayServices.size() > 0) {
            for (GatewayFactoryService config : this.gatewayMappers) {
                if (ConnectorUtils.getClassHierarchy(req).contains(config.getGatewayRequestAllowed())) {
                    synchronized (this.gatewayServices) {
                        return this.gatewayServices.get(config.getGatewayName());
                    }
                }
            }
        } else {
            logger.error("No Gateway mapping or Gateway service has been found");
        }

        return connectorGateway;
    }

}
