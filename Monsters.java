package Pokeright_me;

public class Monsters {
    private int maxHP;
    private int currentHP;
    private int Mana;
    private int maxMana;
    private String Name;
    private int Damage;

    //Getters (Read-Only values)
    public String getName()
    {
        return this.Name;
    }
    public int getmaxHP()
    {
        return this.maxHP;
    }
    public int getcurrentHP()
    {
        return this.currentHP;
    }
    public int getMana()
    {
        return this.Mana;
    }
    public int getmaxMana()
    {
        return this.maxMana;
    }
    public int getDamage()
    {
        return this.Damage;
    }
    
    //Setters (Writeable values)
    public void setDamage(int Damage)
    {
        if (Damage >= 0)
        {
            this.Damage = Damage;
        }
        else
        {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
    }
    public void setmana(int Mana)
    {
        if (Mana >= 0)
        {
            this.Mana = Mana;
        }
        else
        {
            
        }
    }
    public void setmaxMana(int maxMana)
    {
        if (maxMana >= 0)
        {
            this.maxMana = maxMana;
        }
        else
        {
            throw new IllegalArgumentException("Mana cannot be negative");
        }
    }
    public void setcurrentHP(int currentHP)
    {
        if (currentHP >= 0 && currentHP < maxHP)
        {
            this.currentHP = currentHP;
        }
        else
        {
            isDead();
        }
    }
    public void setmaxHP(int maxHP)
    {
        if (maxHP > 0)
        {
            this.maxHP = maxHP;
        }
        else
        {
            throw new IllegalArgumentException("Hp cannot be negative");
        }
    }
    public void setName(String Name)
    {
        if (Name != null)
        {
            this.Name = Name;
        }
        else
        {
            throw new IllegalArgumentException("Name Cannot be Empty");
        }
    }

    //Methods
    protected boolean isDead()
    {
        return !isAlive();
    }
    protected boolean isAlive()
    {   
        var Alive = currentHP > 0;
        return Alive;
    }
    public void inflict_Damage(int damageAmount)
    {
        if (isAlive())
        {
            currentHP -= Damage;
        }
    }
    public void heal(int healAmount)
    {
            if (currentHP < maxHP)
            {
                var healing = currentHP = currentHP + 10;
                if (healing > maxHP)
                {
                    currentHP = maxHP;
                }
            }
    }
    public void recover(int manaAmount)
    {
        if (Mana < maxMana)
        {
            
        }
    }
}
