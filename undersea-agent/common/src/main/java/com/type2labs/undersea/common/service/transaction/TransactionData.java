package com.type2labs.undersea.common.service.transaction;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class TransactionData<T> {

    private final T data;

    private TransactionData(T data) {
        this.data = data;
    }

    public static <T> TransactionData from(T t) {
        return new TransactionData<>(t);
    }

    public T getData() {
        return data;
    }

}
