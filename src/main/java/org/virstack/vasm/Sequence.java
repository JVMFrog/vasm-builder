package org.virstack.vasm;

public class Sequence {
    private Context context;
    private Value middles;

    public Sequence(Context context) {
        this.context = context;
    }

    public Sequence wrap(Value value) {
        middles = value;
        return this;
    }

    private Sequence operation(OperationType type, Value value) {
        Operation operation = context.newOperation();
        operation.type = type;
        operation.mutable = true;
        operation.operands.add(middles);
        operation.operands.add(middles);
        operation.operands.add(value);
        return this;
    }
    public Sequence add(Value value) {
        return operation(OperationType.ADD, value);
    }

    public Sequence minus(Value value) {
        return operation(OperationType.MINUS, value);
    }

    public Sequence mul(Value value) {
        return operation(OperationType.MUL, value);
    }

    public Sequence div(Value value) {
        return operation(OperationType.DIV, value);
    }

    public Value value() {
        return middles;
    }
}
