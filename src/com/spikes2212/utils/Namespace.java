package com.spikes2212.utils;

import java.util.function.Supplier;

public interface Namespace {

    Supplier<Double> addConstantDouble(String name, double value);

    Supplier<Integer> addConstantInt(String name, int value);

    Supplier<String> addConstantString(String name, String value);

    void reset();

    Namespace addChild(String name);
}
