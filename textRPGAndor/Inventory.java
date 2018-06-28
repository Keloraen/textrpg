package textRPGAndor;

import java.util.ArrayList;

public class Inventory {

    private int gold;
    
    public int getGold() {
        return gold;
    }
    
    ArrayList<Item> inv; // Содержимое инвентаря

    public Inventory() {
        gold = 0;
        inv = new ArrayList<>();
    }

    public void addSomeCoins(int amount) {
        gold += amount;
    }

    public void spendCoins(int amount) {
        gold -= amount;
    }

    public boolean isCoinsEnough(int amount) {
        return gold >= amount;
    }

    public void add(Item _newItem) {
        inv.add(_newItem);
    }

    public void showAllItems(boolean a) {
        System.out.println("Инвентарь:");
        if (inv.size() > 0) {
            for (int i = 0; i < inv.size(); i++) {
                System.out.println((i + 1) + ". " + inv.get(i).getName());
            }
        } else {
            System.out.println("Инвентарь пуст");
        }
        if(a){
        System.out.println("0. Закончить осмотр");}
        System.out.println("Золото: " + gold);
    }

    public String useItem(int _itemID) {
        if (_itemID == 0) {
            return "";
        }

        String a = inv.get(_itemID - 1).getName();
        if (inv.get(_itemID - 1).getType() == Item.ItemType.Consumables) {
            inv.remove(_itemID - 1);
        }
        return a;
    }

    public int getSize() {
        return inv.size();
    }

//    public void transferAllItemsToAnotherInventory(Inventory _inv) {
//        for (int i = 0; i < inv.size(); i++) {
//            _inv.add(inv.get(i));
//            _inv.addSomeCoins(gold);
//        }
//    }
}
