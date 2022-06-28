package org.virstack.vasm;

public class Function {

    private Context context;
    private final AsmBuilder builder;

    String name;

    public Function(AsmBuilder builder, String name){
        this.builder = builder;
        this.name = name;
    }

    public Value localValue(Type type, String name){
        Value value = context.newValue();
        value.name = name;
        value.type = type;
        return value;
    }

    public Const localConst(Type type, String name) {
        Const value = new Const();
        value.type = type;
        value.name = name;
        return value;
    }
}
