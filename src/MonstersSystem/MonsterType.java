package MonstersSystem;

//Enumeration for monster types and weakness definition
public enum MonsterType {
    water,
    fire,
    plant;
    private MonsterType weakness;

    static {
        water.weakness = plant;
        fire.weakness = water;
        plant.weakness = fire;
    }

    public MonsterType getWeakness() {
        return weakness;
    }
    
    public boolean isWeakTo(MonsterType other) {
        return this.weakness == other;
    }
}
