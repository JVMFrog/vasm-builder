package org.virstack.vasm;

public class Value {
    Context context;
    public String name;
    public String value;
    public ValueState state;
    public Type type;
    public boolean isLocal;
    public int lastUse;
    public boolean isConst;

    public Sequence add(Value value) {
        return context.newSequence().wrap(this).add(value);
    }

    public Sequence minus(Value value) {
        return context.newSequence().wrap(this).minus(value);
    }

    public Value copy(Value value) {
        this.value = value.value;
        return this;
    }
}
