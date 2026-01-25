package GameMechanics;

import Items.Item;
import MonstersSystem.Monster;
import Utility.GameException;
import Utility.Inventory;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String teamName;
    private final List<Monster> monsters;
    private final Inventory inventory;
    private int credits;

    public Team(String teamName) {
        this.teamName = teamName;
        this.monsters = new ArrayList<>();
        this.inventory = new Inventory();
        this.credits = 500;
    }

    //getters
    public String getTeamName() 
    { 
        return this.teamName; 
    }
    public List<Monster> getMonsters() 
    { 
        return this.monsters; 
    }
    public Inventory getInventory() 
    { 
        return this.inventory; 
    }
    public int getCredits() 
    { 
        return this.credits; 
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }

    public void spendCredits(int amount) throws GameException {
        if (this.credits < amount) {
            throw new GameException("Not enough credits! You have: " + this.credits);
        }
        this.credits -= amount;
    }

    public boolean hasAliveMonster() {
        return monsters.stream().anyMatch(Monster::isAlive);
    }

    public Monster getFirstAliveMonster() {
        return monsters.stream().filter(Monster::isAlive).findFirst().orElse(null);
    }

    public void displayTeam() {
        System.out.println("\n=== TEAM: " + this.teamName + " ===");
        System.out.println("Credits: " + this.credits);
        System.out.println("\nMonsters:");
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            String status = m.isAlive() ? "Alive" : "Dead";
            System.out.printf("%d. %s [%s]%n", i + 1, m, status);
        }
    }

    public void usePotion(int monsterIndex, Item potion) throws GameException {
        if (monsterIndex < 0 || monsterIndex >= monsters.size()) {
            throw new GameException("Bad monster selection");
        }

        Monster monster = monsters.get(monsterIndex);

        if (potion == Item.Revive) {
            if (monster.isAlive()) {
                throw new GameException("What you want to revive his apetite? He's not dead!");
            }
            inventory.useItem(potion);
            monster.revive();
            System.out.println(monster.getName() + " 's soul was resucked into his body!");
            return;
        }

        if (potion == Item.HealthPotion || potion == Item.Super_Potion) {
            if (monster.isDead()) {
                throw new GameException("Have you ever put a band-aid on a dead person?");
            }

            int healAmount = (potion == Item.HealthPotion) ? 50 : 100;
            inventory.useItem(potion);
            monster.heal(healAmount);
            System.out.println(monster.getName() + " was healed by " + healAmount + " HP!");
            return;
        }

        throw new GameException("do you think I'm stupid? This is not a potion!");
    }
}
