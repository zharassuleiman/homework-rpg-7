package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.List;

public class DungeonEngine {
    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventBus eventBus;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventBus eventBus) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventBus = eventBus;
    }

    public EncounterResult run() {
        int maxRounds = 50;
        int round = 1;

        while (round <= maxRounds) {
            System.out.println("\n--- ROUND " + round + " ---");

            for (Hero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) continue;

                int heroDmg = hero.getActiveStrategy().calculateDamage(hero.getAttackPower());
                int bossDef = boss.getActiveStrategy().calculateDefense(boss.getDefense());
                int finalDmg = Math.max(0, heroDmg - bossDef);

                boss.takeDamage(finalDmg);
                eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), finalDmg));

                if (!boss.isAlive()) {
                    eventBus.fireEvent(new GameEvent(GameEventType.BOSS_DEFEATED, boss.getName(), 0));
                    return new EncounterResult(true, round, getAliveHeroesCount());
                }
            }

            if (boss.isAlive()) {
                int bossDmg = boss.getActiveStrategy().calculateDamage(boss.getAttackPower());
                for (Hero hero : heroes) {
                    if (!hero.isAlive()) continue;

                    int heroDef = hero.getActiveStrategy().calculateDefense(hero.getDefense());
                    int finalDmg = Math.max(0, bossDmg - heroDef);

                    int prevHp = hero.getHp();
                    hero.takeDamage(finalDmg);
                    eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), finalDmg));

                    double hpThreshold = hero.getMaxHp() * 0.3;

                    if (!hero.isAlive()) {
                        eventBus.fireEvent(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                    } else if (hero.getHp() < hpThreshold && prevHp >= hpThreshold) {
                        eventBus.fireEvent(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
                    }
                }
            }

            if (getAliveHeroesCount() == 0) {
                return new EncounterResult(false, round, 0);
            }

            round++;
        }

        return new EncounterResult(false, maxRounds, getAliveHeroesCount());
    }

    private int getAliveHeroesCount() {
        return (int) heroes.stream().filter(Hero::isAlive).count();
    }
}

