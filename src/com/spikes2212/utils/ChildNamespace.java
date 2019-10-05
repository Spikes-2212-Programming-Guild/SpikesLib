package com.spikes2212.utils;

import java.util.function.Supplier;

public class ChildNamespace implements Namespace {
    private String name;
    private Namespace parent;
    private String separator;

    public ChildNamespace(String name, Namespace parent, String separator) {
        this.name = name;
        this.parent = parent;
        this.separator = separator;
    }

    public ChildNamespace(String name, Namespace parent) {
        this(name, parent, "/");
    }
    
    @Override
    public Supplier<Double> addConstantDouble(String name, double value) {

        return parent.addConstantDouble(this.name + this.separator + name, value);
    }


    @Override
    public Supplier<Integer> addConstantInt(String name, int value) {

        return parent.addConstantInt(this.name + this.separator + name, value);
    }


    @Override
    public Supplier<String> addConstantString(String name, String value) {

        return parent.addConstantString(this.name + this.separator + name, value);
    }

    @Override
    public ChildNamespace addChild(String name) {

        return new ChildNamespace(name, this);
    }

}
