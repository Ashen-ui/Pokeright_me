package MonsterTypes;

//imports
import MonstersSystem.Monster;
import MonstersSystem.MonsterType;

//Plant monster class
public class PlantMonster extends Monster {
    public PlantMonster(String name, int maxHP, int maxMana, int attackDamage) {
        super(name, MonsterType.plant, maxHP, maxMana, attackDamage);
    }
}
