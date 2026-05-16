package me.mondrilsahu.curseSMP.curse;

public class CurseData {

    private CurseType type;
    private int level;

    public CurseData(CurseType type, int level){
        this.type = type;
        this.level = level;
    }

    public CurseType getType(){
        return type;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = Math.max(1, Math.min(3, level));
    }
}