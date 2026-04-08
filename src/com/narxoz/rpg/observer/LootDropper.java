package com.narxoz.rpg.observer;
import java.util.Random;

public class LootDropper implements GameObserver {
    private final Random random = new Random();

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            System.out.println(" Boss dropped a Phase Crystal!");
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            String[] epicLoot = {"Sword of a Thousand Truths", "Helm of the Cursed", "Ring of Invincibility"};
            System.out.println(" EPIC LOOT DROPPED: " + epicLoot[random.nextInt(epicLoot.length)] + "!");
        }
    }
}
