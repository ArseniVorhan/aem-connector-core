package com.adobe.connector.services.impl;

import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ExecutionPlan;
import com.adobe.connector.WorkUnit;
import com.adobe.connector.gateway.GatewayRequest;
import com.adobe.connector.services.ExecutionPlanBuilder;
import com.adobe.connector.services.ExecutionPlanFactory;
import com.adobe.connector.utils.ConnectorUtils;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component(immediate = true)
@Service(ExecutionPlanBuilder.class)
@References({
        @Reference(name = "executionPlan", referenceInterface = ExecutionPlanFactory.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)})
public class DefaultExecutionPlanBuilder implements ExecutionPlanBuilder {

    protected static final Logger logger = LoggerFactory.getLogger(DefaultExecutionPlanBuilder.class);

    private final List<ExecutionPlanFactory> executionPlanList = new CopyOnWriteArrayList();

    protected void bindExecutionPlan(final ExecutionPlanFactory executionPlanFactory, final Map<String, Object> properties) {
        if (executionPlanFactory != null) {
            this.executionPlanList.add(executionPlanFactory);
        }
    }

    protected void unbindExecutionPlan(final ExecutionPlanFactory executionPlanFactory, final Map<String, Object> properties) {
        if (executionPlanFactory != null) {
            this.executionPlanList.remove(executionPlanFactory);
        }
    }

    @Override
    public ExecutionPlan buildExecutionPlan(ConnectorRequest connectorRequest) {
        // The plan we're building
        ExecutionPlan executionPlan = new ExecutionPlan();

        ExecutionPlanFactory executionPlanFactory = resolveExecutionPlanFactory(connectorRequest);
        if (executionPlanFactory != null) {
            Arrays.stream(executionPlanFactory.getGatewaysName()).forEach(s -> {
                GatewayRequest gatewayRequest = new GatewayRequest(connectorRequest);
                WorkUnit workUnit = new WorkUnit(gatewayRequest, s);
                executionPlan.addWorkUnit(workUnit);
            });
            executionPlan.setResponseCombiner(executionPlanFactory.getResponseCombiner());
        }

        return executionPlan;
    }

    private ExecutionPlanFactory resolveExecutionPlanFactory(ConnectorRequest connectorRequest) {
        for (String className : ConnectorUtils.getClassHierarchy(connectorRequest)) {
            Optional<ExecutionPlanFactory> ExecutionPlanFactory = this.executionPlanList.stream().filter(e -> e.getRequest().equals(className)).findFirst();
            if (ExecutionPlanFactory.isPresent()) {
                return ExecutionPlanFactory.get();
            }
        }
        return null;
    }


}
