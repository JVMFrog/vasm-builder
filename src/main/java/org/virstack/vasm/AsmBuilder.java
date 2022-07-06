package org.virstack.vasm;


import java.util.Arrays;
import java.util.Stack;


public class AsmBuilder {

    private final Assembler assembler = new Assembler();
    private final Stack<Registers> registers = new Stack<>();

    {
        registers.addAll(Arrays.asList(Registers.R0, Registers.R1, Registers.R2, Registers.R3, Registers.R4, Registers.R5));
    }

    public Assembler getAssembler() {
        return assembler;
    }

    public Function beginFunction(String name) {
        return new Function(this, name);
    }

    private Registers allocateRegister() {
        return registers.pop();
    }

    private void loadValue(Value value) {
        if (!value.isLoaded) {
            value.isLoaded = true;
            value.register = allocateRegister();
            if (value.type == Type.WORD)
                assembler.loadWord(value.register, assembler.allocWord(value.name, value.value));
            else if (value.type == Type.HALF) {
                assembler.loadHalf(value.register, assembler.allocHalf(value.name, value.value));
            } else if (value.type == Type.BYTE) {
                assembler.loadByte(value.register, assembler.allocByte(value.name, value.value));
            }
        }
    }

    private void disposeValue(Operation operation, Value value) {

        if (value.lastUse == operation.index) {
            value.isLoaded = false;
            registers.push(value.register);
            value.register = null;
        }
    }

    private void buildSetOperation(Operation operation) {
        Value first = operation.operands.get(0);
        Value second = operation.operands.get(1);


        if (!first.isLoaded && second.isConst)
            first.value = second.value;
        else if (second.isLoaded) {
            first.register = allocateRegister();
            first.isLoaded = true;
            assembler.mov(first.register, second.register);
        }
    }

    private void buildMathOperation(Operation operation, MathOperation mathOperation) {
        Value result = operation.operands.get(0);
        Value first = operation.operands.get(1);
        Value second = operation.operands.get(2);

        loadValue(first);
        loadValue(second);

        if (result.register == null)
            result.register = allocateRegister();

        mathOperation.run(result.register, first.register, second.register);

        disposeValue(operation, result);
        disposeValue(operation, first);
        disposeValue(operation, second);
    }



    public void build(Context context) {
        assembler.begin();
        context.analise();
        for (Operation i : context.getOperations()) {
            if (i.type == OperationType.SET)
                buildSetOperation(i);
            else if (i.type == OperationType.ADD)
                buildMathOperation(i, assembler::add);
            else if (i.type == OperationType.MINUS)
                buildMathOperation(i, assembler::sub);
            else if (i.type == OperationType.MUL)
                buildMathOperation(i, assembler::mul);
            else if (i.type == OperationType.DIV)
                buildMathOperation(i, assembler::div);
        }
        assembler.end();
    }
}


interface MathOperation {
    void run(Registers result, Registers first, Registers second);
}
