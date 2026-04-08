package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;

public class DungeonBoss implements GameObserver {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private int phase;

    private CombatStrategy activeStrategy;
    private final EventBus eventBus;

    public DungeonBoss(String name, int maxHp, int attackPower, int defense, EventBus eventBus) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.eventBus = eventBus;

        this.phase = 1;
        this.activeStrategy = new BossPhase1Strategy();

        this.eventBus.register(this);
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public CombatStrategy getActiveStrategy() { return activeStrategy; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        int previousHp = this.hp;
        hp = Math.max(0, hp - amount);

        double currentPct = (double) hp / maxHp;
        double previousPct = (double) previousHp / maxHp;

        if (previousPct > 0.6 && currentPct <= 0.6 && currentPct > 0.3) {
            phase = 2;
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, phase));
        } else if (previousPct > 0.3 && currentPct <= 0.3 && hp > 0) {
            phase = 3;
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, phase));
        }
    }


    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getSourceName().equals(this.name)) {
            int newPhase = event.getValue();
            if (newPhase == 2) {
                this.activeStrategy = new BossPhase2Strategy();
                System.out.println(" BOSS ENRAGED! Switching to Phase 2: " + activeStrategy.getName());
            } else if (newPhase == 3) {
                this.activeStrategy = new BossPhase3Strategy();
                System.out.println(" BOSS DESPERATE! Switching to Phase 3: " + activeStrategy.getName());
            }
        }
    }
}
