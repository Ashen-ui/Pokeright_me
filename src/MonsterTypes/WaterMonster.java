package MonsterTypes;

//imports
import MonstersSystem.Monster;
import MonstersSystem.MonsterType;

//Water monster class
public class WaterMonster extends Monster {
    public WaterMonster(String name, int maxHP, int maxMana, int attackDamage) {
        super(name, MonsterType.water, maxHP, maxMana, attackDamage);
    }
}
