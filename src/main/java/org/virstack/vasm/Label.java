package org.virstack.vasm;

public class Label {
    int address = 0;

    Label(int address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return Integer.toHexString(address);
    }
}
