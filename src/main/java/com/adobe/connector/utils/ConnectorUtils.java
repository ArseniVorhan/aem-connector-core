package com.adobe.connector.utils;

import com.adobe.connector.gateways.ConnectorRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stievena on 30/09/16.
 */
public class ConnectorUtils {
    public static List<String> getClassHierarchy(ConnectorRequest req) {
        List<String> classHierarchy = new ArrayList<>();
        Class object = req.getClass();
        while (object != null) {
            classHierarchy.add(object.getName());
            object = object.getSuperclass();
        }
        return classHierarchy;
    }
}
