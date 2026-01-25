package MonstersSystem;

//imports
import MonsterTypes.FireMonster;
import MonsterTypes.PlantMonster;
import MonsterTypes.WaterMonster;
import java.util.Random;

//main class
public class BabyMaker {

    //Final random obj gen
    private static final Random random = new Random();

    //Final value defs for random monster names
    private static final String[] WaterNames = { "Squirty", "Tsundere", "Corball", "Nerdi", "Hypoo" };
    private static final String[] FireNames  = { "Infento", "Bluze", "Embar", "PhockX", "Magonda" };
    private static final String[] PlantNames = { "Vapant", "Bossom", "Thornillius", "Oafleaf", "Flopa" };

    //Custom monster for later on
    public static Monster createCustomMonster(MonsterType type, String name) {
        int hp = 100 + random.nextInt(35);
        int mana = 50 + random.nextInt(35);
        int attack = 15 + random.nextInt(25);

        switch (type) {
            case water:
                return new WaterMonster(name, hp, mana, attack);
            case fire:
                return new FireMonster(name, hp, mana, attack);
            case plant:
                return new PlantMonster(name, hp, mana, attack);
            default:
                throw new IllegalStateException("Bad monster type");
        }
    }

    //Random monster for enemies + allies
    public static Monster createRandomMonster() {
        MonsterType[] types = MonsterType.values();
        int index = random.nextInt(types.length);
        MonsterType type = types[index];
        return createMonster(type);
    }

    //Random monster creation
    public static Monster createMonster(MonsterType type) {
        String name;
        int hp = 100 + random.nextInt(20);
        int mana = 50 + random.nextInt(20);
        int attack = 15 + random.nextInt(15);

        switch (type) {
            case water:
                name = WaterNames[random.nextInt(WaterNames.length)];
                return new WaterMonster(name, hp, mana, attack);
            case fire:
                name = FireNames[random.nextInt(FireNames.length)];
                return new FireMonster(name, hp, mana, attack);
            case plant:
                name = PlantNames[random.nextInt(PlantNames.length)];
                return new PlantMonster(name, hp, mana, attack);
            default:
                throw new IllegalStateException("Bad monster type");
        }
    }

    //Creation 
    public static Monster createWildMonster() {
        return createRandomMonster();
    }
}
