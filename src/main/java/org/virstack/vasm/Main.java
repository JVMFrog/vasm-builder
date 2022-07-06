package org.virstack.vasm;

public class Main {
    public static void main(String[] args) {
        AsmBuilder asmBuilder = new AsmBuilder();
        Context context = new Context();

        Value value = context.newValue("a", Type.HALF).set("100");
        Value value2 = context.newValue("b", Type.HALF).set("3");
        Value value3 = context.newValue(Type.HALF);


        value3.set(value.add(value2).value());
        value3.minus(value);



        asmBuilder.build(context);
        System.out.println("middles bvasm:");
        System.out.println(context);
        System.out.println("vasm code:");
        System.out.println(asmBuilder.getAssembler().flush());

        return;
    }
}
