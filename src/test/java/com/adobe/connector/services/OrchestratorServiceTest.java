package com.adobe.connector.services;


import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;
import com.adobe.connector.services.impl.GatewayFactoryServiceImpl;
import com.adobe.connector.services.impl.GatewayResolverImpl;
import com.adobe.connector.services.impl.OrchestratorServiceImpl;
import com.google.common.collect.ImmutableMap;
import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Assert;
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

    private OrchestratorService orchestratorService = new OrchestratorServiceImpl();

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

        ConnectorGateway mockGatewayAlpha = Mockito.mock(ConnectorGateway.class);
        Mockito.when(mockGatewayAlpha.getName()).thenReturn("MockGatewayAlpha");
        context.registerService(ConnectorGateway.class, mockGatewayAlpha);

        ConnectorGateway mockGatewayBeta = Mockito.mock(ConnectorGateway.class);
        Mockito.when(mockGatewayBeta.getName()).thenReturn("MockGatewayBeta");
        context.registerService(ConnectorGateway.class, mockGatewayBeta);

        context.registerInjectActivateService(new GatewayFactoryServiceImpl(), ImmutableMap.<String, Object>builder()
                .put("name", "MockGatewayAlpha")
                .put("request.allowed", "com.adobe.connector.gateways.ConnectorRequest")
                .build());

        context.registerInjectActivateService(new GatewayFactoryServiceImpl(), ImmutableMap.<String, Object>builder()
                .put("name", "MockGatewayBeta")
                .put("request.allowed", "com.adobe.connector.gateways.ConnectorRequest")
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
