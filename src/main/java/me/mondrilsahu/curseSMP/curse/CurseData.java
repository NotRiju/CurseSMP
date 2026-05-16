/*
 * Decompiled with CFR 0.152.
 */
package me.mondrilsahu.curseSMP.curse;

import me.mondrilsahu.curseSMP.curse.CurseType;

public class CurseData {
    private CurseType type;
    private int level;

    public CurseData(CurseType type, int level) {
        this.type = type;
        this.level = level;
    }

    public CurseType getType() {
        return this.type;
    }

    public void setType(CurseType type) {
        this.type = type;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = Math.min(3, Math.max(1, level));
    }
}

