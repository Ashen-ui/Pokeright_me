package Utility;

import GameMechanics.Team;
import Items.Item;
import java.util.Scanner;

public class Shop {
    private final Team team;
    private final Scanner scanner;

    public Shop(Team team, Scanner scanner) {
        this.team = team;
        this.scanner = scanner;
    }

    public void openShop() {
        boolean shopping = true;

        while (shopping) {
            System.out.println("\n=== SHOP ===");
            System.out.println("Your credits: " + team.getCredits());
            System.out.println("\n1. " + Item.HealthPotion.getItemName() + " - " + Item.HealthPotion.getPrice() + " credits");
            System.out.println("2. " + Item.Super_Potion.getItemName() + " - " + Item.Super_Potion.getPrice() + " credits");
            System.out.println("3. " + Item.Revive.getItemName() + " - " + Item.Revive.getPrice() + " credits");
            System.out.println("4. " + Item.MonsterTrap.getItemName() + " - " + Item.MonsterTrap.getPrice() + " credits");
            System.out.println("5. Exit Shop");
            System.out.print("Choose an item to buy: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 5) {
                    shopping = false;
                    continue;
                }

                Item item;
                switch (choice) {
                    case 1: item = Item.HealthPotion; 
                        break;
                    case 2: item = Item.Super_Potion; 
                        break;
                    case 3: item = Item.Revive; 
                        break;
                    case 4: item = Item.MonsterTrap; 
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }

                System.out.print("How many? ");
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0) {
                    System.out.println("Invalid quantity!");
                    continue;
                }

                int totalCost = item.getPrice() * quantity;

                try {
                    team.spendCredits(totalCost);
                    team.getInventory().addItem(item, quantity);
                    System.out.println("Purchased " + quantity + "x " + item.getItemName()
                        + " for " + totalCost + " credits!");
                } catch (GameException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}
