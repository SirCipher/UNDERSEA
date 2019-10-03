package com.type2labs.undersea.utilities.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.utilities.exception.UnderseaException;

public class UnderseaExceptionHandler {

    public static void handle(Throwable t, Agent agent) {
        if (t == null | !(t instanceof UnderseaException)) {
            return;
        }

        UnderseaException underseaException = (UnderseaException) t;
        if (underseaException.getAgentService() == null) {
            return;
        }

        switch (underseaException.getErrorCode()) {
            case SERVICE_FAILED:
                agent.serviceManager().handleFailure(underseaException.getAgentService().getClass());
                break;
            case UNKNOWN:
                agent.serviceManager().handleFailure(underseaException.getAgentService().getClass());
                break;
            default:
                t.printStackTrace();
        }
    }

}
