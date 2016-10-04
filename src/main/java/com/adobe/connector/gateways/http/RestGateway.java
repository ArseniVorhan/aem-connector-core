package com.adobe.connector.gateways.http;

import com.adobe.connector.gateways.ConnectorGateway;
import com.adobe.connector.gateways.ConnectorRequest;
import com.adobe.connector.gateways.ConnectorResponse;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

public abstract class RestGateway implements ConnectorGateway {

    private static final Logger logger = LoggerFactory.getLogger(RestGateway.class);

    private OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }

    @Override
    public void executeRequest(final ConnectorRequest req, final ConnectorResponse res) throws Exception {
        Optional<Worker> worker = getWorker(req);
        if (worker.isPresent()) {
            Request request = new Request.Builder().url(resolveUrl(worker.get(), req)).headers(buildHttpHeaders()).build();
            Response response = getHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                worker.get().getProcessor().process(response.body().byteStream(), req, res);
            } else {
                logger.error("Error when requesting '" + resolveUrl(worker.get(), req) + "' - Reason " + response.message());
            }
        } else {
            logger.error("Cannot find any worker the request '" + req.getClass().getName() + "'. Make sure that a processor is defined for that request");
        }
    }

    private Headers buildHttpHeaders() {
        Headers.Builder builder = new Headers.Builder();
        getHttpHeaders().forEach((key, value) -> builder.add(key, value));
        return builder.build();
    }

    private String resolveUrl(Worker worker, ConnectorRequest req) {
        MessageFormat messageFormat = new MessageFormat(worker.getUrl());
        return messageFormat.format(((RestRequest) req).getParameters());
    }

    protected abstract Map<String, String> getHttpHeaders();

    protected abstract Optional<Worker> getWorker(ConnectorRequest req);

}
