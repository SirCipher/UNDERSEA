package com.type2labs.undersea.dsl.uuv.model;

import com.type2labs.undersea.common.agent.AgentImpl;
import com.type2labs.undersea.common.agent.Node;
import com.type2labs.undersea.common.agent.Sensor;

import java.util.Iterator;

public class DslAgentProxy extends AgentImpl {

    private static final long serialVersionUID = -4045369914177688638L;
    private String metaFileName;


    public DslAgentProxy(String name) {
        super(name);
    }

    public void assignRandomNodes(int n, double latLowerBound, double latUpperBound, double longLowerBound,
                                  double longUpperBound) {
        for (int i = 0; i < n; i++) {
            this.assignNode(Node.randomNode(0.0, 150.0, -140.0, 0.0));
        }
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

}
