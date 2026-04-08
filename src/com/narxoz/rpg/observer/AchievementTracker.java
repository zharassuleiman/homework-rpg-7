package com.narxoz.rpg.observer;

public class AchievementTracker implements GameObserver {
    private boolean firstBloodUnlocked = false;
    private boolean bossSlayerUnlocked = false;
    private boolean sacrificeUnlocked = false;

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                if (!firstBloodUnlocked) {
                    System.out.println(" ACHIEVEMENT UNLOCKED: First Blood!");
                    firstBloodUnlocked = true;
                }
                break;
            case BOSS_DEFEATED:
                if (!bossSlayerUnlocked) {
                    System.out.println(" ACHIEVEMENT UNLOCKED: Boss Slayer!");
                    bossSlayerUnlocked = true;
                }
                break;
            case HERO_DIED:
                if (!sacrificeUnlocked) {
                    System.out.println(" ACHIEVEMENT UNLOCKED: The Ultimate Sacrifice...");
                    sacrificeUnlocked = true;
                }
                break;
        }
    }
}
