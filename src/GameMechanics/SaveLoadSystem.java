package GameMechanics;

import MonsterTypes.FireMonster;
import MonsterTypes.PlantMonster;
import MonsterTypes.WaterMonster;
import MonstersSystem.Monster;
import java.io.*;

public class SaveLoadSystem {
    private static final String SAVE_FILE = "savegame.csv";

    public static void saveGame(Team team) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            String safeTeamName = team.getTeamName().replace(",", " ");

            writer.println("TEAM," + safeTeamName + "," + team.getCredits());

            for (Monster monster : team.getMonsters()) {
                writer.println(monster.toCSV());
            }

            writer.println("INVENTORY," + team.getInventory().toCSV());
            System.out.println("\nGame saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static Team loadGame() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("No save file found.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null || !line.startsWith("TEAM,")) {
                System.out.println("Invalid save file format (missing TEAM line).");
                return null;
            }

            String[] teamData = line.split(",", 3);
            if (teamData.length < 3) {
                System.out.println("Invalid TEAM line.");
                return null;
            }

            String teamName = teamData[1];
            int savedCredits = Integer.parseInt(teamData[2]);

            Team team = new Team(teamName);

            int diff = savedCredits - team.getCredits();
            if (diff != 0) team.addCredits(diff);

            boolean inventoryLoaded = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("INVENTORY,")) {
                    String inventoryData = line.substring("INVENTORY,".length());
                    team.getInventory().loadFromCSV(inventoryData);
                    inventoryLoaded = true;
                    break;
                }

                String[] data = line.split(",", 7);
                if (data.length != 7) {
                    continue;
                }

                String monsterClass = data[0];
                String name = data[1];

                int currentHP = Integer.parseInt(data[2]);
                int maxHP     = Integer.parseInt(data[3]);
                int mana      = Integer.parseInt(data[4]);
                int maxMana   = Integer.parseInt(data[5]);
                int attack    = Integer.parseInt(data[6]);

                Monster monster = createMonsterFromClass(monsterClass, name, maxHP, maxMana, attack);
                if (monster == null) {
                    continue;
                }

                int damage = maxHP - currentHP;
                if (damage > 0) {
                    monster.takeDamage(damage);
                }

                try {
                    monster.setMana(mana);
                } catch (Exception e) {
                }
                team.addMonster(monster);
            }

            if (!inventoryLoaded) {
                team.getInventory().loadFromCSV("");
            }

            System.out.println("\nGame loaded successfully!");
            return team;

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    private static Monster createMonsterFromClass(String monsterClass, String name, int maxHP, int maxMana, int attack) {
        switch (monsterClass) {
            case "WaterMonster": return new WaterMonster(name, maxHP, maxMana, attack);
            case "FireMonster":  return new FireMonster(name, maxHP, maxMana, attack);
            case "PlantMonster": return new PlantMonster(name, maxHP, maxMana, attack);
            default: return null;
        }
    }
}