package com.spikes2212.utils;

import edu.wpi.first.wpilibj.Preferences;

import java.util.function.Supplier;

public class RootNamespace implements Namespace {

    @Override
    public Supplier<Double> addConstantDouble(String name, double value) {
        addConstantDouble(name, value);
        return () -> Preferences.getInstance().getDouble(name, value);
    }

    @Override
    public Supplier<Integer> addConstantInt(String name, int value) {
        addConstantInt(name, value);
        return () -> Preferences.getInstance().getInt(name, value);

    }

    @Override
    public Supplier<String> addConstantString(String name, String value) {
        addConstantString(name, value);
        return () -> Preferences.getInstance().getString(name, value);

    }

    @Override
    public Namespace addChild(String name) {
        return new ChildNamespace(name, this);
    }
}
