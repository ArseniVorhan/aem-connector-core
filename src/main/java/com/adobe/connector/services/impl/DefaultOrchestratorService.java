package com.adobe.connector.services.impl;

import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ConnectorResponse;
import com.adobe.connector.ExecutionPlan;
import com.adobe.connector.ResponseCombiner;
import com.adobe.connector.gateway.Gateway;
import com.adobe.connector.services.ExecutionPlanBuilder;
import com.adobe.connector.services.OrchestratorService;
import com.adobe.connector.utils.PerformanceLogger;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(immediate = true)
@Service(OrchestratorService.class)
@References({
        @Reference(name = "gateway", referenceInterface = Gateway.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC),
        @Reference(name = "responseCombiner", referenceInterface = ResponseCombiner.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)})
public class DefaultOrchestratorService implements OrchestratorService {

    private final static Logger logger = LoggerFactory.getLogger(OrchestratorService.class);
    private static final PerformanceLogger perfLog = PerformanceLogger.getLogger();

    @Reference
    private ExecutionPlanBuilder executionPlanBuilder;

    private final Map<String, Gateway> gatewayMap = new ConcurrentHashMap();
    private final Map<String, ResponseCombiner> responseCombinerMap = new ConcurrentHashMap();

    protected void bindGateway(final Gateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            this.gatewayMap.put(gateway.getName(), gateway);
        }
    }

    protected void unbindGateway(final Gateway gateway, final Map<String, Object> properties) {
        if (gateway != null) {
            this.gatewayMap.remove(gateway.getName());
        }
    }

    protected void bindResponseCombiner(final ResponseCombiner responseCombiner, final Map<String, Object> properties) {
        if (responseCombiner != null) {
            this.responseCombinerMap.put(responseCombiner.getName(), responseCombiner);
        }
    }

    protected void unbindResponseCombiner(final ResponseCombiner responseCombiner, final Map<String, Object> properties) {
        if (responseCombiner != null) {
            this.responseCombinerMap.remove(responseCombiner.getName());
        }
    }


    @Override
    public ConnectorResponse execute(final ConnectorRequest req) throws RuntimeException {
        PerformanceLogger.enable();
        try {
            perfLog.start();

            perfLog.start();
            ExecutionPlan executionPlan = executionPlanBuilder.buildExecutionPlan(req);
            perfLog.stop(9999, "Time to build the execution plan");

            Map<String, ConnectorResponse> connectorResponseList = new ConcurrentHashMap<>();
            executionPlan.getWorkUnits().parallelStream().forEach(workUnit -> {
                Gateway gateway = gatewayMap.get(workUnit.getGateway());
                perfLog.start();
                ConnectorResponse connectorResponse = gateway.execute(workUnit.getGatewayRequest()).getConnectorResponse();
                perfLog.stop(9999, "Time spent in the gateway", gateway.getName());
                connectorResponseList.put(gateway.getName(), connectorResponse);
            });

            return handleConnectorResponses(connectorResponseList, req, executionPlan.getResponseCombiner());
        } catch (Throwable e) {
            logger.error("Unable to perform the request " + req, e);
        } finally {
            perfLog.stop(9999, "Time spent in the connector");
            PerformanceLogger.clean();
        }
        return null;
    }

    private ConnectorResponse handleConnectorResponses(Map<String, ConnectorResponse> connectorResponses, ConnectorRequest request, String responseCombiner) {
        if (connectorResponses.size() > 0) {
            if (responseCombiner != null && responseCombinerMap.containsKey(responseCombiner)) {
                return responseCombinerMap.get(responseCombiner).combine(connectorResponses, request);
            }
            return connectorResponses.values().stream().findFirst().get();
        }
        return null;
    }
}
