package com.adobe.connector.services.impl;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.services.GatewayResolver;
import org.apache.felix.scr.annotations.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(immediate = true, configurationFactory = true)
@Service(GatewayResolver.class)
@References({
        @Reference(name = "gatewayMapper", referenceInterface = GatewayFactoryServiceImpl.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC),
        @Reference(name = "gatewayService", referenceInterface = ConnectorGateway.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)})
public class GatewayResolverImpl implements GatewayResolver {

    private final static Logger logger = LoggerFactory.getLogger(GatewayResolverImpl.class);

    private final List<GatewayFactoryServiceImpl> gatewayMappers = new ArrayList<>();
    private final Map<String, ConnectorGateway> gatewayServices = new HashMap<>();

    protected void bindGatewayMapper(final GatewayFactoryServiceImpl configService, final Map<String, Object> properties) {
        if (configService != null) {
            synchronized (this.gatewayMappers) {
                this.gatewayMappers.add(configService);
            }
        }
    }

    protected void unbindGatewayMapper(final GatewayFactoryServiceImpl configService, final Map<String, Object> properties) {
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

        for (GatewayFactoryServiceImpl config : this.gatewayMappers) {
            try {
                Class<ConnectorRequest> acceptedRequest = (Class<ConnectorRequest>) Class.forName(config.getGatewayRequestAllowed());
                // TODO: Use instance of instead of the request name
                if (req.getClass().getSuperclass().getName().equals(acceptedRequest.getName())) {
                    String gatewayPath = this.gatewayServices.get(config.getGatewayName()).getClass().getName();
                    return getConnectorGateway(gatewayPath);
                }
            } catch (ClassNotFoundException e) {
                logger.error("Unable to instanciate class " + config.getGatewayRequestAllowed(), e);
            }
        }

        return connectorGateway;
    }

    public ConnectorGateway getConnectorGateway(String gatewayPath) {
        BundleContext bundleContext = getBundleContext();
        ServiceReference reference = bundleContext.getServiceReference(gatewayPath);
        ConnectorGateway connectorGateway = (ConnectorGateway) bundleContext.getService(reference);
        return connectorGateway;
    }

    @Override
    public void releaseGateway(ConnectorGateway gateway) {
        BundleContext bundleContext = getBundleContext();
        ServiceReference reference = bundleContext.getServiceReference(gateway.getClass().toString());
        bundleContext.ungetService(reference);
    }

    /**
     * Return the bundle context.
     *
     * @return
     */
    private BundleContext getBundleContext() {
        BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
        return bundleContext;
    }

}
