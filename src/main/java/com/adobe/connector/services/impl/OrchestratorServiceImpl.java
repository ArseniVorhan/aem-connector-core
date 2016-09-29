package com.adobe.connector.services.impl;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;
import com.adobe.connector.services.GatewayResolver;
import com.adobe.connector.services.OrchestratorService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component(immediate = true)
@Service(OrchestratorService.class)
public class OrchestratorServiceImpl implements OrchestratorService {

    private final static Logger logger = LoggerFactory.getLogger(OrchestratorService.class);

    @Reference
    private GatewayResolver gatewayResolver;

    @Override
    public void execute(final ConnectorRequest req, final ConnectorResponse res) throws RuntimeException {
        Optional<ConnectorGateway> resolvedGateway = gatewayResolver.resolve(req);
        if (resolvedGateway.isPresent()) {
            ConnectorGateway gateway = resolvedGateway.get();
            try {
                gateway.executeRequest(req, res);
            } catch (Exception e) {
                logger.error("Exception when executing request", e);
            }
        } else {
            logger.error("Unable to find a gateway resolving the request type " + req.getClass().getName());
            throw new RuntimeException("Unable to find a gateway");
        }

    }
}
