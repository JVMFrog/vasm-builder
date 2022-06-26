package org.virstack.vasm;

public class AsmBuilder {
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
        builder.append(".stack\n")
                .append(stackSection)
                .append(".code\n")
                .append(codeSection);
    }

    private Label stackLabel(String code, int size) {
        stackSection.append("\t").append(code).append("\n");
        lastStackAddress +=size;
        return new Label(lastStackAddress - size);
    }

    public Label allocWord(long value) {
        return stackLabel("word " +value, 8);
    }

    public Label allocHalf(int value) {
        return stackLabel("half " +value, 4);
    }

    public Label allocByte(byte value) {
        return stackLabel("byte " +value, 1);
    }

    public Label label() {
        return new Label(lastCodeAddress);
    }
    private void operation(String opcode, Registers result, Registers first, Registers second) {
        lastCodeAddress += 4;
        codeSection.append("\t")
                .append(opcode).append(" ")
                .append(result).append(" ")
                .append(first).append(" ")
                .append(second).append("\n");
    }

    private void operation(String opcode, Registers first, Label address) {
        lastCodeAddress += 6;
        codeSection.append("\t")
                .append(opcode).append(" ")
                .append(first).append(" ")
                .append(address).append("\n");
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

    public void loadWord(Registers register, Label label) {
        operation("ld", register,  label);
    }

    public void loadHalf(Registers register, Label label) {
        operation("ldh", register,  label);
    }

    public void loadByte(Registers register, Label label) {
        operation("ldb", register,  label);
    }

    public String build() {
        return builder.toString();
    }
}
