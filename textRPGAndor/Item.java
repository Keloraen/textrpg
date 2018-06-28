package textRPGAndor;


public class Item {
    
    //конечные, бесконечные, квеcтовые, броня, оружие, сила
    public static enum ItemType { Consumables, InfConsumables, Quest, Armor, Weapon, Strength };
    
    private final String name;
    private final ItemType type;
    
    public ItemType getType()
    {
        return type;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Item(String _name, ItemType _type)
    {
        name = _name;
        type = _type;
    }
}
