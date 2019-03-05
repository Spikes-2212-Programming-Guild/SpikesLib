package com.spikes2212.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;

public class MultiCommand extends ConditionalCommandWrapper {
    public MultiCommand(Supplier<Integer> indexSupplier, Command... commands) {
    	super(commands[0], (commands.length == 2) ? commands[1] : new MultiCommand(reduceByOne(indexSupplier), removeFirst(commands)), () -> indexSupplier.get() == 0);
    }
    
    private static Command[] removeFirst(Command... ts) {
    	List<Command> list = Arrays.asList(ts);
    	list.remove(0);
    	Command[] temp = null;
    	list.toArray(temp);
    	return temp;
    }
    
    private static Supplier<Integer> reduceByOne(Supplier<Integer> supplier) {
    	return () -> supplier.get() - 1;
    }
}
