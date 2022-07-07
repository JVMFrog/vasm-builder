package org.virstack.vasm;

public class Value {
    Context context;
    public String name;
    public String value;
    public ValueState state;
    public Type type;
    public boolean isLocal;
    int lastUse;
    boolean isConst;
    boolean isLoaded;
    boolean isAllocated;
    Registers register = null;

    public Sequence add(Value value) {
        return context.newSequence().wrap(this).add(value);
    }

    public Sequence minus(Value value) {
        return context.newSequence().wrap(this).minus(value);
    }

    public Value set(Value value) {
        Operation operation = context.newOperation();
        operation.type = OperationType.SET;

        operation.operands.add(this);
        operation.operands.add(value);


        return this;
    }

    public Value set(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        String[] array = super.toString().split("\\.");
        return array[array.length-1].toLowerCase();
    }
}
