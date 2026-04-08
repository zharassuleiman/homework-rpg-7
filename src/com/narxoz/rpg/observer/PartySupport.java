package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PartySupport implements GameObserver {
    private final List<Hero> party;
    private final Random random = new Random();

    public PartySupport(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            List<Hero> aliveHeroes = party.stream().filter(Hero::isAlive).collect(Collectors.toList());
            if (!aliveHeroes.isEmpty()) {
                Hero target = aliveHeroes.get(random.nextInt(aliveHeroes.size()));
                int healAmount = 30;
                target.heal(healAmount);
                System.out.println(" [PartySupport] Healed " + target.getName() + " for " + healAmount + " HP!");
            }
        }
    }
}
