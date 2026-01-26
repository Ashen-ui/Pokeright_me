package Utility;

import Items.Item;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<Item, Integer> items;

    public Inventory() {
        items = new HashMap<>();
        items.put(Item.HealthPotion, 5);
        items.put(Item.MonsterTrap, 10);
    }

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public boolean hasItem(Item item) {
        return items.getOrDefault(item, 0) > 0;
    }

    public void useItem(Item item) throws GameException {
        if (!hasItem(item)) {
            throw new GameException("You don't have any " + item.getItemName());
        }
        items.put(item, items.get(item) - 1);
    }

    public void display() {
        System.out.println("\n=== INVENTORY ===");
        for (Item item : Item.values()) {
            int qty = items.getOrDefault(item, 0);
            if (qty > 0) {
                System.out.printf("%s x%d%n", item.getItemName(), qty);
            }
        }
    }
    public String toCSV() {
        StringBuilder SaveBuild = new StringBuilder();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getValue() > 0) {
                SaveBuild.append(entry.getKey().name()).append(",").append(entry.getValue()).append(";");
            }
        }
        return SaveBuild.toString();
    }

    public void loadFromCSV(String csv) {
        items.clear();
        if (csv == null || csv.trim().isEmpty()) return;

        String[] itemPairs = csv.split(";");
        for (String pair : itemPairs) {
            String[] parts = pair.split(",");
            if (parts.length == 2) {
                try {
                    Item item = Item.valueOf(parts[0]);
                    int quantity = Integer.parseInt(parts[1]);
                    items.put(item, quantity);
                } catch (Exception ignored) {}
            }
        }
    }
}