package org.virstack.vasm;

public class Main {
    public static void main(String[] args) {
        AsmBuilder builder = new AsmBuilder();
        builder.begin();
        Label a = builder.allocHalf(100);
        Label b = builder.allocHalf(200);

        builder.loadHalf(Registers.R0, a);
        builder.loadHalf(Registers.R1, b);
        builder.add(Registers.R2, Registers.R0, Registers.R1);
        builder.end();
        System.out.println(builder.build());
    }
}
