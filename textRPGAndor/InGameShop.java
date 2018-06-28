package textRPGAndor;

public class InGameShop {
    
    public final int ITEMS_COUNT = 10;
    private final Item[] itm = new Item[ITEMS_COUNT];
    private final int[] itmCost = new int[ITEMS_COUNT];
    private int goldAmount;
    
    public InGameShop(){
        goldAmount = 10;
        itm[0] = new Item("Слабое зелье лечения", Item.ItemType.Consumables);
        itmCost[0] = 2;
        itm[1] = new Item("Щит доблести", Item.ItemType.Armor);
        itmCost[1] = 2;
        itm[2] = new Item("Железная лопата", Item.ItemType.InfConsumables);
        itmCost[2] = 5;
        itm[3] = new Item("Сила (1 ед.)", Item.ItemType.Strength);
        itmCost[3] = 2;
    }
    
    public void showItems(Hero h){
        System.out.println(h.getName() + " находится в лавке торговца. В кармане монет: " + h.myInv.getGold() + ".\n Ассортимент:");
        for(int i=0;i<ITEMS_COUNT;i++)
        {
            System.out.println((i + 1) + ". " + itm[i].getName() + " - " + itmCost[i] + " монеты");    
        }
    }
    
    public void buyByHero(int index, Hero h)
    {
        if(h.myInv.isCoinsEnough(itmCost[index]))
        {
            goldAmount += itmCost[index];
            h.myInv.spendCoins(itmCost[index]);
            if(itm[index].getType() != Item.ItemType.Strength){
            h.myInv.add(itm[index]);
            System.out.println(h.getName() + " покупает " + itm[index].getName());            
            } else {
                h.strength++;
                System.out.println(h.getName() + " становится сильнее на 1 пункт");
            }

        }
        else
            System.out.println("Не хватает золота");
    }
    
}
