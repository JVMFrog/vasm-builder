package org.virstack.vasm;

public enum Type {
    REGISTER,
    WORD,
    HALF,
    BYTE;
    public String getPrefix() {
        if (this == WORD)
            return "";
        else if (this == HALF)
            return "h";
        else if (this == BYTE)
            return "b";
        return null;
    }
}
