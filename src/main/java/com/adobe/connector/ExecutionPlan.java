package com.adobe.connector;

import java.util.ArrayList;
import java.util.List;

public class ExecutionPlan {

    private List<WorkUnit> workUnits = new ArrayList();

    private String responseCombiner;

    public void addWorkUnit(WorkUnit workUnit) {
        workUnits.add(workUnit);
    }

    public List<WorkUnit> getWorkUnits() {
        return workUnits;
    }

    public String getResponseCombiner() {
        return responseCombiner;
    }

    public void setResponseCombiner(String responseCombiner) {
        this.responseCombiner = responseCombiner;
    }

    public boolean isEmpty() {
        return workUnits.isEmpty();
    }

    public int getNumberOfWorkUnits() {
        return (workUnits == null) ? 0 : workUnits.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder().append("(ExecutionPlan): [");

        for (WorkUnit workUnit : workUnits) {
            sb.append(workUnit.toString()).append(",");
        }
        return sb.append("]").toString();
    }
}
