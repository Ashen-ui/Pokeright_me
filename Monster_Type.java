package Pokeright_me;
    public enum Monster_Type 
    {
        water,
        fire,
        plant;
        private Monster_Type Weakness;
    
    static 
    {
        water.Weakness = plant;
        fire.Weakness = water;
        plant.Weakness = fire;
    }
    public Monster_Type getWeakElement() 
    {
        return Weakness;
    }
}


