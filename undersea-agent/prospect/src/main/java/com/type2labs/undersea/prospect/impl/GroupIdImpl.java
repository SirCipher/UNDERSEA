package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.GroupId;

public class GroupIdImpl implements GroupId {

    private final String name;
    private final String uuid;

    public GroupIdImpl(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String uuid() {
        return uuid;
    }
}
