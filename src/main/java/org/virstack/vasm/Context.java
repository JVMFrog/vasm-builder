package org.virstack.vasm;

import java.util.LinkedList;
import java.util.List;

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

    public Value newValue(Type type) {
        Value value = new Value();
        value.context = this;
        value.type = type;
        values.add(value);
        return value;
    }


    public Value newValue(String name, Type type) {
        Value value = newValue(type);
        value.name = name;
        return value;
    }

    public Value intermediateValue() {
        Value value = newValue(null);
        value.state = ValueState.INTERMEDIATE;
        return value;
    }

    public Value allocConst(Type type, String value) {
        Value constValue = newValue(type);
        constValue.value = value;
        constValue.isConst = true;
        return constValue;
    }

    public void analise() {
        int operationIndex = 0;
        for (Operation i : operations) {
            if (i.mutable)
                i.operands.get(0).isConst = false;

            for (Value j : i.operands)
                j.lastUse = operationIndex;

            i.index = operationIndex;
            operationIndex++;
        }
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Value> getValues() {
        return values;
    }



    @Override
    public String toString() {
        int noNameVariable = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("variables:\n");

        for (Value i : values) {
            builder.append("\t")
                    .append(i.type.toString().toLowerCase())
                    .append(" ")
                    .append(i.name != null ? i.name : i.toString())
                    .append(" ")
                    .append(i.value)
                    .append("\n");
        }
        noNameVariable = 0;
        builder.append("code:\n");
        for (Operation i : operations) {
            builder.append("\t").append((i.type + "").toLowerCase()).append(" ");
            for (Value val : i.operands) {
                builder.append(val.name != null ? val.name : val.toString()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
