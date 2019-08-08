package com.type2labs.undersea.models.missionplanner;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlannerException extends Exception {

    public PlannerException(String message, Throwable e) {
        super(message, e);
    }

    public PlannerException(Throwable e) {
        super(e);
    }

}
