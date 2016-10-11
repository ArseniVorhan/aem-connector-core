package com.adobe.connector.gateways.connection.http;

import com.adobe.connector.gateways.message.Message;
import com.adobe.connector.gateways.message.RestMessage;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by stievena on 10/10/16.
 */
@Component(immediate = true)
@Service(value = HttpEndpointClient.class)
public class OkHttpEndpointClient implements HttpEndpointClient {

    private final static Logger logger = LoggerFactory.getLogger(OkHttpEndpointClient.class);
    private final OkHttpClient httpClient = new OkHttpClient();

    private OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public HttpResponse getHttpResponse(Message message) {
        Response response = null;
        try {
            Request request = new Request.Builder().url(message.getMessageAsString()).headers(buildHttpHeaders(((RestMessage) message).getHeader())).build();
            response = getHttpClient().newCall(request).execute();
            byte[] responseData = null;
            if (response.isSuccessful()) {
                responseData = response.body().bytes();
            }
            return new HttpResponse(responseData, response.code(), response.message());
        } catch (Throwable e) {
            logger.error("Error when executing request " + message.getMessageAsString() + " with headers " + ((RestMessage) message).getHeader().toString(), e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    private Headers buildHttpHeaders(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        headers.forEach((key, value) -> builder.add(key, value));
        return builder.build();
    }
}
