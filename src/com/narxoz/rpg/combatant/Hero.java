package com.narxoz.rpg.combatant;
import com.narxoz.rpg.strategy.CombatStrategy;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy activeStrategy;


    public Hero(String name, int hp, int attackPower, int defense, CombatStrategy initialStrategy) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.activeStrategy = initialStrategy;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public CombatStrategy getActiveStrategy() { return activeStrategy; }

    public void setStrategy(CombatStrategy strategy) {
        this.activeStrategy = strategy;
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
}
