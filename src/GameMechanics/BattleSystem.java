package GameMechanics;

//imports
import Items.Item;
import MonstersSystem.BabyMaker;
import MonstersSystem.Monster;
import Utility.GameException;
import java.util.Random;
import java.util.Scanner;

//battle class
public class BattleSystem {
    private final Team playerTeam;
    private Monster wildMonster;
    private final Scanner scanner;
    private static final Random random = new Random();

    //main battle system construct
    public BattleSystem(Team playerTeam, Scanner scanner) {
        this.playerTeam = playerTeam;
        this.scanner = scanner;
    }

    //...yeah battle start duh
    public void startBattle() {
        this.wildMonster = BabyMaker.createWildMonster();
        System.out.println("\nA wild " + wildMonster.getName() + " appeared!");
        System.out.println(this.wildMonster);

        Monster currentMonster = playerTeam.getFirstAliveMonster();
        if (currentMonster == null) {
            System.out.println("All your monsters are dead. You cannot battle with a corpse.");
            return;
        }

        boolean battleOngoing = true;

        while (battleOngoing && wildMonster.isAlive() && playerTeam.hasAliveMonster()) {
            System.out.println("\n--- Your turn ---");
            System.out.println("Current monster: " + currentMonster);
            System.out.println("Wild " + this.wildMonster);

            System.out.println("\n1. Attack");
            System.out.println("2. Use Item");
            System.out.println("3. Switch Monster");
            System.out.println("4. Try to Capture");
            System.out.println("5. Run Away");
            System.out.print("Choose an action: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        try {
                            int damage = currentMonster.attack(wildMonster);
                            if (wildMonster.getType().isWeakTo(currentMonster.getType())) {
                                System.out.println("It's super effective!");
                            }
                            System.out.println(currentMonster.getName() + " dealt " + damage + " damage!");

                            if (wildMonster.isDead()) {
                                System.out.println("\nYou defeated " + wildMonster.getName() + "!");
                                int creditsEarned = 50 + random.nextInt(51);
                                playerTeam.addCredits(creditsEarned);
                                System.out.println("You earned " + creditsEarned + " credits!");
                                battleOngoing = false;
                                continue;
                            }
                        } catch (GameException e) {
                            System.out.println("Error: " + e.getMessage());
                            continue;
                        }
                        break;

                    case 2:
                        currentMonster = useItemInBattle(currentMonster);
                        break;

                    case 3:
                        currentMonster = switchMonster();
                        if (currentMonster == null) {
                            continue;
                        }
                        break;

                    case 4:
                        if (tryCapture()) {
                            battleOngoing = false;
                            continue;
                        }
                        break;

                    case 5:
                        System.out.println("You ran away like a little bitch...");
                        battleOngoing = false;
                        continue;

                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }

                if (wildMonster.isAlive() && currentMonster.isAlive()) {
                    try {
                        int damage = wildMonster.attack(currentMonster);
                        if (currentMonster.getType().isWeakTo(wildMonster.getType())) {
                            System.out.println("It's super cancerous!");
                        }
                        System.out.println("\nWild " + wildMonster.getName() + " attacked for " + damage + " damage!");

                        if (currentMonster.isDead()) {
                            System.out.println(currentMonster.getName() + " was knocked out!");

                            if (!playerTeam.hasAliveMonster()) {
                                System.out.println("\nAll your monsters are dead, are you happy?");
                                battleOngoing = false;
                            } else {
                                System.out.println("Choose another slav...erm Monster!");
                                currentMonster = switchMonster();
                                if (currentMonster == null) {
                                    battleOngoing = false;
                                }
                            }
                        }
                    } catch (GameException e) {
                        System.out.println("Wild monster error: " + e.getMessage());
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private Monster useItemInBattle(Monster currentMonster) {
        playerTeam.getInventory().display();
        System.out.println("\n1. Potion");
        System.out.println("2. Super Potion");
        System.out.println("3. Revive");
        System.out.println("4. Mana Potion");
        System.out.println("5. Cancel");
        System.out.print("Choose an item: ");

        try {
            int itemChoice = Integer.parseInt(scanner.nextLine());
            Item item;

            switch (itemChoice) {
                case 1: 
                    item = Item.HealthPotion; 
                    break;
                case 2: 
                    item = Item.SuperPotion; 
                    break;
                case 3: 
                    item = Item.Revive; 
                    break;
                case 4:
                    item = Item.ManaPotion;
                    break;
                case 5: 
                    return currentMonster;
                default:
                    System.out.println("Invalid choice!");
                    return currentMonster;
            }

            playerTeam.displayTeam();
            System.out.print("Choose monster (1-" + playerTeam.getMonsters().size() + "): ");
            int monsterIndex = Integer.parseInt(scanner.nextLine()) - 1;

            playerTeam.usePotion(monsterIndex, item);
            
            // If the current monster was revived or healed, update reference
            if (monsterIndex >= 0 && monsterIndex < playerTeam.getMonsters().size()) {
                Monster targetMonster = playerTeam.getMonsters().get(monsterIndex);
                if (targetMonster.isAlive()) {
                    return targetMonster;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (GameException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return currentMonster;
    }

    private Monster switchMonster() {
        playerTeam.displayTeam();
        System.out.print("Choose a monster to switch to (1-" + playerTeam.getMonsters().size() + "): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < playerTeam.getMonsters().size()) {
                Monster selected = playerTeam.getMonsters().get(choice);
                if (selected.isAlive()) {
                    System.out.println("Switched to " + selected.getName() + "!");
                    return selected;
                } else {
                    System.out.println("You can't play with a corpse!");
                }
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }

        return playerTeam.getFirstAliveMonster();
    }

    private boolean tryCapture() {
        try {
            if (!wildMonster.canBeCaptured()) {
                throw new GameException("Cannot capture! This sonovabitch is too healthy!");
            }

            playerTeam.getInventory().useItem(Item.MonsterTrap);

            System.out.println("\nYou threw a Monster Trap at " + wildMonster.getName());
            System.out.println("\n...");

            if (Math.random() < 0.7) {
                playerTeam.addMonster(wildMonster);
                System.out.println("Success! " + wildMonster.getName() + " was captured!");
                return true;
            } else {
                System.out.println("Oh no! " + wildMonster.getName() + " broke free!");
                return false;
            }

        } catch (GameException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}