package com.adobe.connector.gateways.connection.http;

import com.adobe.connector.gateways.message.HttpMessage;
import com.adobe.connector.gateways.message.Message;
import okhttp3.*;
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
        HttpMessage httpMessage = (HttpMessage) message;
        Response response = null;
        try {
            Request.Builder requestBuilder = new Request.Builder().url(httpMessage.getUrl()).headers(buildHttpHeaders(httpMessage.getHeaders()));
            RequestBody requestBody = null;
            if (httpMessage.getFormParameters().size() > 0) {
                FormBody.Builder formBody = new FormBody.Builder();
                httpMessage.getFormParameters().forEach((s, s2) -> formBody.add(s, s2));
                requestBody = formBody.build();
            } else if (httpMessage.getBody() != null) {
                requestBody = RequestBody.create(MediaType.parse(httpMessage.getMediaType()), httpMessage.getBody());
            }

            switch (httpMessage.getHttpMethod()) {
                case POST:
                    if (requestBody != null) {
                        requestBuilder.post(requestBody);
                    }
                    break;
                case PUT:
                    if (requestBody != null) {
                        requestBuilder.put(requestBody);
                    }
                    break;
                case DELETE:
                    if (requestBody != null) {
                        requestBuilder.delete(requestBody);
                    } else {
                        requestBuilder.delete();
                    }
                    break;
                case PATCH:
                    if (requestBody != null) {
                        requestBuilder.patch(requestBody);
                    }
                    break;
            }

            Request request = requestBuilder.build();
            response = getHttpClient().newCall(request).execute();
            byte[] responseData = null;
            if (response.isSuccessful()) {
                responseData = response.body().bytes();
            }
            return new HttpResponse(responseData, response.code(), response.message());
        } catch (Throwable e) {
            logger.error("Error when executing request " + httpMessage.getUrl() + " with headers " + httpMessage.getHeaders().toString(), e);
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
