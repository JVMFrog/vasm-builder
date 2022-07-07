package org.virstack.vasm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AsmBuilder asmBuilder = new AsmBuilder();
        Context context = new Context();

        Value value = context.newValue("a", Type.HALF).set("0");

        ArrayList<Value> values = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Value value1 = context.newValue("var"+i, Type.HALF).set(i+"");
            values.add(value1);
            value.add(value1);
        }


        values.forEach(value::add);


        asmBuilder.build(context);
        System.out.println("middles bvasm:");
        System.out.println(context);
        System.out.println("vasm code:");
        System.out.println(asmBuilder.getAssembler().flush());
    }
}
