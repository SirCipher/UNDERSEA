package com.type2labs.undersea.utilities;

public interface AbstractFactory<T> {

    T get(String name);

}
