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
        if(this.name == null) {
            return ConstantHandler.addConstantDouble(name, value);
        }
        return parent.addConstantDouble(this.name + "-" + name, value);
    }

    @Override
    public Supplier<Integer> addConstantInt(String name, int value) {
        if(this.name == null) {
            return ConstantHandler.addConstantInt(name, value);
        }
        return parent.addConstantInt(this.name + "-" + name, value);
    }

    @Override
    public Supplier<String> addConstantString(String name, String value) {
        if(this.name == null) {
            return ConstantHandler.addConstantString(name, value);
        }

        return parent.addConstantString(this.name + "-" + name, value);
    }

    @Override
    public void reset() {

    }


    @Override
    public Namespace assignParent(String parent) {
        this.parent = new ChildNamespace(parent);
        return this.parent;
    }

    public ChildNamespace addChild(String name) {

    }

}
