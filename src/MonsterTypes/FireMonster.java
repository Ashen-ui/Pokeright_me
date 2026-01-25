package MonsterTypes;

//imports
import MonstersSystem.Monster;
import MonstersSystem.MonsterType;

//Fire monster class
public class FireMonster extends Monster {
    public FireMonster(String name, int maxHP, int maxMana, int attackDamage) {
        super(name, MonsterType.fire, maxHP, maxMana, attackDamage);
    }
}
