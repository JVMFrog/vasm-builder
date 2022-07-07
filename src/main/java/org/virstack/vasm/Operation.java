package org.virstack.vasm;

import java.util.ArrayList;
import java.util.LinkedList;

public class Operation {
    public ArrayList<Value> operands = new ArrayList<>();
    public OperationType type;
    public boolean mutable;
    int index;
}
