package org.virstack.vasm;

public class Assembler {
    private StringBuilder builder = new StringBuilder();
    private StringBuilder stackSection = new StringBuilder();
    private StringBuilder codeSection = new StringBuilder();

    private int lastStackAddress = 0;
    private int lastCodeAddress = 0;

    public void begin() {
        stackSection = new StringBuilder();
        codeSection = new StringBuilder();
    }


    public void end() {
        builder.append("section .stack\n")
                .append(stackSection)
                .append("section .code\n")
                .append(codeSection);
    }

    private Label stackLabel(String name, String code) {
        stackSection.append(name).append(":\n").append("\t").append(code).append("\n");
        return new Label(name);
    }

    public Label alloc(Type type, String name, String value) {
        return stackLabel(name, (type+" ").toLowerCase() +value);
    }

    public Label allocHalf(String name, String value) {
        return stackLabel(name, "half " +value);
    }

    public Label allocByte(String name, String value) {
        return stackLabel(name,"byte " +value);
    }

    public Label label(String name) {
        codeSection.append(name).append(":\n");
        return new Label(name);
    }
    private void operation(String opcode, Registers result, Registers first, Registers second) {
        codeSection.append("\t")
                .append(opcode).append(" ")
                .append(result).append(", ")
                .append(first).append(", ")
                .append(second).append("\n");
    }

    private void operation(String opcode, Registers first, Label address) {
        codeSection.append("\t")
                .append(opcode).append(" ")
                .append(first).append(", ")
                .append(address).append("\n");
    }

    public void mov(Registers direct, Registers source) {
        codeSection.append("\t").append("mov ").append(direct).append(", ").append(source).append("\n");
    }

    public void add(Registers result, Registers first, Registers second) {
        operation("add", result, first, second);
    }

    public void sub(Registers result, Registers first, Registers second) {
        operation("sub", result, first, second);
    }

    public void mul(Registers result, Registers first, Registers second) {
        operation("mul", result, first, second);
    }

    public void div(Registers result, Registers first, Registers second) {
        operation("div", result, first, second);
    }

    public void bigger(Registers result, Registers first, Registers second) {
        operation("cmpb", result, first, second);
    }

    public void less(Registers result, Registers first, Registers second) {
        operation("cmpl", result, first, second);
    }

    public void equals(Registers result, Registers first, Registers second) {
        operation("cmp", result, first, second);
    }

    public void load(Type type, Registers register, Label label) {
        operation("ld" + type.getPrefix(), register,  label);
    }

    public void store(Type type, Registers register, Label label) {
        operation("str" + type.getPrefix(), register, label);
    }

    public String flush() {
        return builder.toString();
    }
}
