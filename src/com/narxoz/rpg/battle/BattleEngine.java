package com.narxoz.rpg.battle;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {}

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        EncounterResult result = new EncounterResult();
        int round = 1;

        result.addLog("Battle starts between Team A and Team B!");

        while (isAnyAlive(teamA) && isAnyAlive(teamB)) {
            result.addLog("--- Round " + round + " ---");


            performTurn(teamA, teamB, result);

            if (isAnyAlive(teamB)) {
                performTurn(teamB, teamA, result);
            }

            round++;
            if (round > 100) break;
        }

        result.setRounds(round - 1);
        result.setWinner(isAnyAlive(teamA) ? "Team A (Heroes)" : "Team B (Enemies)");
        result.addLog("Battle ended. Winner: " + result.getWinner());

        return result;
    }

    private void performTurn(List<Combatant> attackers, List<Combatant> defenders, EncounterResult result) {
        for (Combatant attacker : attackers) {
            if (attacker.isAlive()) {
                List<Combatant> aliveDefenders = defenders.stream()
                        .filter(Combatant::isAlive)
                        .collect(Collectors.toList());

                if (!aliveDefenders.isEmpty()) {
                    Combatant target = aliveDefenders.get(random.nextInt(aliveDefenders.size()));
                    int damage = attacker.getAttackPower();
                    target.takeDamage(damage);

                    result.addLog(attacker.getName() + " attacks " + target.getName() + " for " + damage + " damage.");

                    if (!target.isAlive()) {
                        result.addLog(target.getName() + " has been defeated!");
                    }
                }
            }
        }
    }

    private boolean isAnyAlive(List<Combatant> team) {
        return team.stream().anyMatch(Combatant::isAlive);
    }
}