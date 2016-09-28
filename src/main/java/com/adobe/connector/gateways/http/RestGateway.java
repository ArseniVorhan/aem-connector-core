package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Rest gateway.
 *
 * @author kassa
 */
public abstract class RestGateway implements ConnectorGateway {

    private OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }

    @Override
    public void executeRequest(ConnectorRequest req, ConnectorResponse res) throws Exception {
        String url = extractRequestUrl(req);
        Request request = new Request.Builder().url(url).headers(buildHttpHeaders()).build();
        Response response = getHttpClient().newCall(request).execute();
        res = buildConnectorResponse(response);
    }

    protected abstract String extractRequestUrl(ConnectorRequest req);

    protected abstract ConnectorResponse buildConnectorResponse(Response response);

    protected abstract Headers buildHttpHeaders();

}
