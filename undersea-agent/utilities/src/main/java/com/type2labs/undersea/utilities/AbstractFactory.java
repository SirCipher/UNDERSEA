package com.type2labs.undersea.utilities;

import java.util.List;

public interface AbstractFactory<T> {

    T get(String name);

    List<T> createN(int n);

}
