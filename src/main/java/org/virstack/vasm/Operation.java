package org.virstack.vasm;

import java.util.LinkedList;

public class Operation {
    public LinkedList<Value> operands = new LinkedList<>();
    public OperationType type;
    public boolean mutable;
    int index;
}
