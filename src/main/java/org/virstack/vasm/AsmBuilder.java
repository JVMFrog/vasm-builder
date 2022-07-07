package org.virstack.vasm;


import java.util.Arrays;
import java.util.ListIterator;
import java.util.Stack;


public class AsmBuilder {

    private final Assembler assembler = new Assembler();
    private final Stack<Registers> registers = new Stack<>();
    private final Stack<Value> valuesUsingRegisters = new Stack<>();
    private final Stack<Value> nearValuesUsingRegisters = new Stack<>();
    int operationIndex = 0;
    private Context context;

    {
        registers.addAll(Arrays.asList(Registers.R0, Registers.R1, Registers.R2, Registers.R3, Registers.R4, Registers.R5));
    }

    public Assembler getAssembler() {
        return assembler;
    }

    public Function beginFunction(String name) {
        return new Function(this, name);
    }

    private void storeValue(Value value) {
        value.isLoaded = false;

        assembler.store(value.type, value.register, new Label(value.name));
    }
    private void allocateRegister(Value value) {
        if (registers.size() > 0) {
            value.register = registers.pop();
            valuesUsingRegisters.push(value);
            return;
        }


        Value storable = null;

        ListIterator<Operation> iterator = context.getOperations().listIterator(operationIndex);
        nearValuesUsingRegisters.addAll(valuesUsingRegisters);

        while (iterator.hasNext()) {
            Operation operation = iterator.next();
            for (int i = 0; i < operation.operands.size(); i++) {
                Value val = operation.operands.get(i);
                if (nearValuesUsingRegisters.remove(val)) {
                    storable = val;
                }

            }
        }

        value.register = storable.register;
        storeValue(storable);

        storable.register = null;
        valuesUsingRegisters.push(value);
        valuesUsingRegisters.remove(storable);

    }

    private void loadValue(Value value) {
        if (!value.isLoaded) {
            value.isLoaded = true;
            allocateRegister(value);
            assembler.load(value.type, value.register, value.isAllocated ? new Label(value.name) : assembler.alloc(value.type, value.name, value.value));
            value.isAllocated = true;
        }
    }

    private void disposeValue(Operation operation, Value value) {
        if (value.lastUse == operation.index) {
            valuesUsingRegisters.remove(value);
            registers.push(value.register);
            value.register = null;
            value.isLoaded = false;
        }
    }

    private void buildSetOperation(Operation operation) {
        Value first = operation.operands.get(0);
        Value second = operation.operands.get(1);


        if (!first.isLoaded && second.isConst)
            first.value = second.value;
        else if (second.isLoaded) {
            allocateRegister(first);
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
            allocateRegister(result);

        mathOperation.run(result.register, first.register, second.register);

        disposeValue(operation, result);
        disposeValue(operation, first);
        disposeValue(operation, second);
    }


    public void build(Context context) {
        this.context = context;
        assembler.begin();
        context.analise();
        operationIndex = 0;

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
            operationIndex++;
        }
        assembler.end();
    }
}


interface MathOperation {
    void run(Registers result, Registers first, Registers second);
}
