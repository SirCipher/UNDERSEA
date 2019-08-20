package com.type2labs.undersea.common.service;

/**
 * enum for services to extend for their own status codes
 */
public enum TransactionStatusCode {
    ELECTED_LEADER,
    DISTRIBUTE_MISSION,
    NOT_LEADER,
    FAILING
}
