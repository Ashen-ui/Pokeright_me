package Items;

public enum Item {
    
    //enumerating items
    HealthPotion("Health Potion", 10, "Heals 50 HP"),
    Super_Potion("Super Potion", 50, "Heals 100 HP"),
    ManaPotion("Mana Potion", 20, "Restores 50 MP"),
    Revive("Revive", 150, "Revives dead monster with 50% HP"),
    MonsterTrap("Trap", 15, "Captures a monster with <=30% HP");

    private final String name;
    private final int price;
    private final String description;

    //creating items
    Item(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    //getters
    public String getItemName() 
    { 
        return this.name; 
    }
    public int getPrice() 
    { 
        return this.price; 
    }
    public String getDescription() 
    { 
        return this.description; 
    }
}
