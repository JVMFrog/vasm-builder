package org.virstack.vasm;

import java.util.LinkedList;

public class Context {
    private LinkedList<Operation> operations = new LinkedList<>();
    private LinkedList<Value> values = new LinkedList<>();

    public void clear() {
        values.clear();
        operations.clear();
    }

    public Operation newOperation() {
        Operation operation = new Operation();
        operations.add(operation);
        return operation;
    }

    public Sequence newSequence() {
        return new Sequence(this);
    }

    public Value newValue() {
        Value value = new Value();
        value.context = this;
        values.add(value);
        return value;
    }

    public Value intermediateValue() {
        Value value = newValue();
        value.state = ValueState.INTERMEDIATE;
        return value;
    }
}
