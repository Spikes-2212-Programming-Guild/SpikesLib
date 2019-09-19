package com.spikes2212.utils;

import com.spikes2212.dashboard.ConstantHandler;

import java.util.function.Supplier;

public class ChildNamespace implements Namespace {
    private String name;
    private Namespace parent;

    public ChildNamespace(String name) {
        this.name = name;
    }

    public ChildNamespace(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Supplier<Double> addConstantDouble(String name, double value) {

        return parent.addConstantDouble(this.name + " " + name, value);
    }


    @Override
    public Supplier<Integer> addConstantInt(String name, int value) {

        return parent.addConstantInt(this.name + " " + name, value);
    }


    @Override
    public Supplier<String> addConstantString(String name, String value) {

        return parent.addConstantString(this.name + " " + name, value);
    }

    @Override
    public ChildNamespace addChild(String name) {

        return new ChildNamespace(name, this);
    }

}
