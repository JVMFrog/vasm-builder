package org.virstack.vasm;

public class Label {
    String name;

    Label(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
