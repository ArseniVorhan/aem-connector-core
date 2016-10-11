package com.adobe.connector.services;


import com.adobe.connector.gateways.Gateway;
import com.adobe.connector.ConnectorRequest;
import com.adobe.connector.ConnectorResponse;
import com.adobe.connector.services.impl.ExecutionPlanFactoryImpl;
import com.adobe.connector.services.impl.GatewayResolverImpl;
import com.adobe.connector.services.impl.DefaultOrchestratorService;
import com.google.common.collect.ImmutableMap;
import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrchestratorServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private OrchestratorService orchestratorService = new DefaultOrchestratorService();

    private GatewayResolver gatewayResolver = new GatewayResolverImpl();

    @Mock
    ConnectorRequest mockRequestAlpha;
    @Mock
    ConnectorRequest mockRequestBeta;

    @Mock
    ConnectorResponse mockResponse;

    @Before
    public void setUp() throws Exception {

        context.registerInjectActivateService(gatewayResolver);

        Gateway mockGatewayAlpha = Mockito.mock(Gateway.class);
        Mockito.when(mockGatewayAlpha.getName()).thenReturn("MockGatewayAlpha");
        context.registerService(Gateway.class, mockGatewayAlpha);

        Gateway mockGatewayBeta = Mockito.mock(Gateway.class);
        Mockito.when(mockGatewayBeta.getName()).thenReturn("MockGatewayBeta");
        context.registerService(Gateway.class, mockGatewayBeta);

        context.registerInjectActivateService(new ExecutionPlanFactoryImpl(), ImmutableMap.<String, Object>builder()
                .put("name", "MockGatewayAlpha")
                .put("request.allowed", "com.adobe.connector.ConnectorRequest")
                .build());

        context.registerInjectActivateService(new ExecutionPlanFactoryImpl(), ImmutableMap.<String, Object>builder()
                .put("name", "MockGatewayBeta")
                .put("request.allowed", "com.adobe.connector.ConnectorRequest")
                .build());

        context.registerInjectActivateService(orchestratorService);


    }

    @Test
    public void testResolver() {
//        GatewayResolver gatewayResolver = context.getService(GatewayResolver.class);
//        Assert.assertEquals("MockGatewayAlpha", gatewayResolver.resolve(mockRequestAlpha).get().getName());
//        Assert.assertEquals("MockGatewayBeta", gatewayResolver.resolve(mockRequestBeta).get().getName());
    }

    @Test()
    public void testOrchestrator() {
//        exception.expect(RuntimeException.class);
//        exception.expectMessage("Unable to find a gateway");
//
//        OrchestratorService orchestratorService = context.getService(OrchestratorService.class);
//
//        ConnectorRequest mockRequest = Mockito.mock(ConnectorRequest.class);
//        orchestratorService.execute(mockRequest, mockResponse);
    }

}
