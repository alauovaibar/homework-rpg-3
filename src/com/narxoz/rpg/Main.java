package com.narxoz.rpg;

import com.narxoz.rpg.adapter.EnemyCombatantAdapter;
import com.narxoz.rpg.adapter.HeroCombatantAdapter;
import com.narxoz.rpg.battle.BattleEngine;
import com.narxoz.rpg.battle.Combatant;
import com.narxoz.rpg.battle.EncounterResult;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.hero.Mage;
import com.narxoz.rpg.hero.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Battle Engine Demo ===\n");

        // TODO: Create heroes and enemies
        Warrior warrior = new Warrior("Arthas");
        Mage mage = new Mage("Jaina");
        Goblin goblin = new Goblin();

        // TODO: Wrap with adapters
        List<Combatant> heroes = new ArrayList<>();
        heroes.add(new HeroCombatantAdapter(warrior));
        heroes.add(new HeroCombatantAdapter(mage));

        List<Combatant> enemies = new ArrayList<>();
        enemies.add(new EnemyCombatantAdapter(goblin));

        // TODO: Demonstrate Singleton behavior
        BattleEngine engineA = BattleEngine.getInstance();
        BattleEngine engineB = BattleEngine.getInstance();
        System.out.println("Same instance? " + (engineA == engineB));
        System.out.println();

        // TODO: Run battle and print summary
        engineA.setRandomSeed(42L);
        EncounterResult result = engineA.runEncounter(heroes, enemies);

        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        for (String line : result.getBattleLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");

        enemies.add(new EnemyCombatantAdapter(new Goblin()));
        enemies.get(1).takeDamage(-20);

        result = engineA.runEncounter(heroes, enemies);

        System.out.println("Battle Summary:");
        System.out.println("---------------");
        for (String line : result.getBattleLog()) {
            System.out.println(line);
        }
        System.out.println("---------------");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Total Rounds: " + result.getRounds());

        System.out.println("\n=== Demo Complete ===");
    }
}
