package com.type2labs.undersea.utilities;

import java.util.List;

public interface AbstractFactory<T> {

    T get(String name);

    T create();

    List<T> createN(int n);

}
