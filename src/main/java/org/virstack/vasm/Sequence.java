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

    public Sequence add(Value value) {
        Operation operation = context.newOperation();
        operation.type = OperationType.ADD;
        operation.operands.add(middles);
        operation.operands.add(middles);
        operation.operands.add(value);
        return this;
    }

    public Sequence minus(Value value) {
        Operation operation = context.newOperation();
        operation.type = OperationType.MINUS;
        operation.operands.add(middles);
        operation.operands.add(middles);
        operation.operands.add(value);
        return this;
    }

    public Sequence mul(Value value) {
        Operation operation = context.newOperation();
        operation.type = OperationType.MUL;
        operation.operands.add(middles);
        operation.operands.add(middles);
        operation.operands.add(value);
        return this;
    }

    public Sequence div(Value value) {
        Operation operation = context.newOperation();
        operation.type = OperationType.DIV;
        operation.operands.add(middles);
        operation.operands.add(middles);
        operation.operands.add(value);
        return this;
    }
}
