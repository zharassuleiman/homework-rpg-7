package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;

public class HeroStatusMonitor implements GameObserver {
    private final List<Hero> party;

    public HeroStatusMonitor(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED) {
            System.out.println(" --- HERO STATUS UPDATE ---");
            for (Hero h : party) {
                System.out.printf("  %s: %d/%d HP (%s)%n",
                        h.getName(), h.getHp(), h.getMaxHp(), h.isAlive() ? "Alive" : "DEAD");
            }
            System.out.println("-----------------------------");
        }
    }
}
