package com.adobe.connector;

import java.util.List;

/**
 * Connector response object.
 *
 * @author kassa
 */
public abstract class ConnectorResponse<T> {
    public abstract List<T> getResults();
}
