package MonstersSystem;

//imports
import Utility.GameException;

//General Monster structure (different from the rest of the 3 types)
public abstract class Monster {
    private String Name;
    private int currentHP;
    private int maxHP;
    private int Mana;
    private int maxMana;
    private int attackDamage;
    private MonsterType Type;

    //Construction of the Monster (certain values are set to "this" while others use setters for trusted values)
    public Monster(String Name, MonsterType Type, int maxHP, int maxMana, int attackDamage) {
        setName(Name);
        this.Type = Type;
        setMaxHP(maxHP);
        this.currentHP = maxHP;
        setMaxMana(maxMana);
        this.Mana = maxMana;
        setAttackDamage(attackDamage);
    }

    //Getters
    public String getName() 
    { 
        return this.Name; 
    }
    public int getCurrentHP() 
    { 
        return this.currentHP; 
    }
    public int getMaxHP() 
    { 
        return this.maxHP; 
    }
    public int getMana() 
    { 
        return this.Mana; 
    }
    public int getMaxMana() 
    { 
        return this.maxMana; 
    }
    public int getAttackDamage() 
    { 
        return this.attackDamage; 
    }
    public MonsterType getType() 
    { 
        return this.Type; 
    }

    //Setters + error validation attempts    
    private void setName(String Name) {
        if (Name == null || Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.Name = Name;
    }

    private void setMaxHP(int maxHP) {
        if (maxHP <= 0) throw new IllegalArgumentException("Max HP must be positive");
        this.maxHP = maxHP;
    }

    private void setMaxMana(int maxMana) {
        if (maxMana < 0) throw new IllegalArgumentException("Max mana cannot be negative");
        this.maxMana = maxMana;
    }

    private void setAttackDamage(int attackDamage) {
        if (attackDamage < 0) throw new IllegalArgumentException("Attack damage cannot be negative");
        this.attackDamage = attackDamage;
    }

    public boolean isAlive() 
    { 
        return currentHP > 0; 
    }
    public boolean isDead() 
    { 
        return !isAlive(); 
    }

    public boolean canBeCaptured() {
        return currentHP <= (maxHP * 0.3);
    }

    //METHODS

    //Attack method with error validation
    public int attack(Monster target) throws GameException {
        if (this.isDead()) {
            throw new GameException(this.Name + " is dead stoopid");
        }
        if (target.isDead()) {
            throw new GameException(target.getName() + " you gonna beat his soul too ?");
        }

        int damage = calculateDamage(target);
        target.takeDamage(damage);
        return damage;
    }

    //Calcing dmg with type advantage
    protected int calculateDamage(Monster target) {
        int baseDamage = this.attackDamage;
        if (target.getType().isWeakTo(this.Type)) {
            return (int)(baseDamage * 1.3);
        }
        return baseDamage;
    }

    //Dmg apply
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }

    //Healing with error validation
    public void heal(int amount) throws GameException {
        if (this.currentHP >= this.maxHP) {
            throw new GameException("Monster " + this.Name + " he is FED with HP already");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("What are you healing with negative values?");
        }
        this.currentHP += amount;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }

    //Revive with half hp
    public void revive() {
        this.currentHP = this.maxHP / 2;
    }

    //Mana restoration
    public void restoreMana(int amount) {
        this.Mana += amount;
        if (this.Mana > this.maxMana) {
            this.Mana = this.maxMana;
        }
    }

    //Formatting override for proper display
    @Override
    public String toString() {
        return String.format("%s - HP: %d/%d", Name, currentHP, maxHP);
    }

    public String toCSV() {
        return String.format("%s,%s,%d,%d,%d,%d,%d",
            getClass().getSimpleName(), Name, currentHP, maxHP, Mana, maxMana, attackDamage);
    }
}
