package com.spikes2212.utils;

import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.Preferences;

import java.util.function.Supplier;

public class RootNamespace implements Namespace {

    @Override
    public Supplier<Double> addConstantDouble(String name, double value) {
        return ConstantHandler.addConstantDouble(name, value);
    }

    @Override
    public Supplier<Integer> addConstantInt(String name, int value) {
        return ConstantHandler.addConstantInt(name, value);
    }

    @Override
    public Supplier<String> addConstantString(String name, String value) {
       return ConstantHandler.addConstantString(name, value);
    }

    @Override
    public Namespace addChild(String name) {
        return new ChildNamespace(name, this);
    }
}
