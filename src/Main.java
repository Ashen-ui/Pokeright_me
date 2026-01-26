import GameMechanics.BattleSystem;
import GameMechanics.SaveLoadSystem;
import GameMechanics.Team;
import Items.Item;
import MonstersSystem.BabyMaker;
import MonstersSystem.Monster;
import Utility.GameException;
import Utility.Shop;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Team playerTeam;

    public static void main(String[] args) {
        System.out.println("+-----------------------------------+");
        System.out.println("|             WELCOME!              |");
        System.out.println("+-----------------------------------+");

        mainMenu();
        scanner.close();
    }

    private static void mainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        newGame();
                        gameLoop();
                        break;
                    case 2:
                        playerTeam = SaveLoadSystem.loadGame();
                        if (playerTeam != null) {
                            gameLoop();
                        }
                        break;
                    case 3:
                        System.out.println("Thanks for playing!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! enter a number.");
            }
        }
    }

    private static void newGame() {
        System.out.print("\nEnter your team name: ");
        String teamName = scanner.nextLine();

        if (teamName.trim().isEmpty()) {
            teamName = "Team IgnoringInput";
        }

        playerTeam = new Team(teamName);

        //Genreate 3 starters
        System.out.println("\nCreating Team Starters for: " + teamName);
        for (int i = 0; i < 3; i++) {
            Monster monster = BabyMaker.createRandomMonster();
            playerTeam.addMonster(monster);
            System.out.println("You got: " + monster);
        }

        System.out.println("\nAhh shit, here we go again!");
    }

    private static void gameLoop() {
        boolean playing = true;

        while (playing) {
            System.out.println("\n=== GAME MENU ===");
            System.out.println("1. View Team");
            System.out.println("2. Battle Wild Monster");
            System.out.println("3. Use Item");
            System.out.println("4. Shop");
            System.out.println("5. Create Custom Monster");
            System.out.println("6. Save Game");
            System.out.println("7. Return to Main Menu");
            System.out.print("Choose an action: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        playerTeam.displayTeam();
                        playerTeam.getInventory().display();
                        break;

                    case 2:
                        if (!playerTeam.hasAliveMonster()) {
                            System.out.println("All your monsters are dead bruh");
                        } else {
                            BattleSystem battle = new BattleSystem(playerTeam, scanner);
                            battle.startBattle();
                        }
                        break;

                    case 3:
                        useItemOutsideBattle();
                        break;

                    case 4:
                        Shop shop = new Shop(playerTeam, scanner);
                        shop.openShop();
                        break;

                    case 5:
                        createCustomMonster();
                        break;

                    case 6:
                        SaveLoadSystem.saveGame(playerTeam);
                        break;

                    case 7:
                        System.out.println("Returning to main menu");
                        playing = false;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! enter a number.");
            }
        }
    }

    private static void useItemOutsideBattle() {
        if (playerTeam == null) {
            System.out.println("Create or load a team first.");
            return;
        }
        if (playerTeam.getMonsters().isEmpty()) {
            System.out.println("You have no monsters.");
            return;
        }
        
        playerTeam.displayTeam();
        System.out.print("Choose monster (1-" + playerTeam.getMonsters().size() + "): ");
        
        try {
            int monsterIndex = Integer.parseInt(scanner.nextLine()) - 1;

            playerTeam.getInventory().display();
            System.out.println("\n1. Potion");
            System.out.println("2. Super Potion");
            System.out.println("3. Revive");
            System.out.println("4. Mana Potion");
            System.out.println("5. Cancel");
            System.out.print("Choose an item: ");

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
                    return;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            playerTeam.usePotion(monsterIndex, item);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (GameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createCustomMonster() {
        if (playerTeam == null) {
            System.out.println("Create or load a team first.");
            return;
        }

        System.out.println("\n=== CREATE CUSTOM MONSTER ===");
        System.out.println("Choose monster type:");
        System.out.println("1. Water");
        System.out.println("2. Fire");
        System.out.println("3. Plant");
        System.out.print("Enter choice: ");

        try {
            int typeChoice = Integer.parseInt(scanner.nextLine());
            MonstersSystem.MonsterType type;

            switch (typeChoice) {
                case 1:
                    type = MonstersSystem.MonsterType.water;
                    break;
                case 2:
                    type = MonstersSystem.MonsterType.fire;
                    break;
                case 3:
                    type = MonstersSystem.MonsterType.plant;
                    break;
                default:
                    System.out.println("Invalid type!");
                    return;
            }

            System.out.print("Enter monster name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            Monster customMonster = BabyMaker.createCustomMonster(type, name);
            playerTeam.addMonster(customMonster);

            System.out.println("\nCustom monster created!");
            System.out.println(customMonster);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! enter a number.");
        } catch (Exception e) {
            System.out.println("Error creating monster: " + e.getMessage());
        }
    }
}