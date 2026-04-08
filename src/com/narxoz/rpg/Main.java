package com.narxoz.rpg;
import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        List<Hero> party = new ArrayList<>();
        Hero arthur = new Hero("Arthur", 100, 30, 15, new BalancedStrategy());
        Hero berserker = new Hero("Grom", 80, 45, 5, new AggressiveStrategy());
        Hero tank = new Hero("Braum", 150, 15, 30, new DefensiveStrategy());

        party.add(arthur);
        party.add(berserker);
        party.add(tank);

        DungeonBoss boss = new DungeonBoss("Cursed Lich", 400, 25, 10, eventBus);

        eventBus.register(new BattleLogger());
        eventBus.register(new AchievementTracker());
        eventBus.register(new PartySupport(party));
        eventBus.register(new HeroStatusMonitor(party));
        eventBus.register(new LootDropper());

        System.out.println("=== ENCOUNTER STARTS ===");

        arthur.setStrategy(new AggressiveStrategy());
        System.out.println(arthur.getName() + " changed strategy to: " + arthur.getActiveStrategy().getName());

        DungeonEngine engine = new DungeonEngine(party, boss, eventBus);
        EncounterResult result = engine.run();

        System.out.println("\n=== ENCOUNTER FINISHED ===");
        System.out.println("Heroes Won: " + result.isHeroesWon());
        System.out.println("Rounds Played: " + result.getRoundsPlayed());
        System.out.println("Surviving Heroes: " + result.getSurvivingHeroes());
    }
}