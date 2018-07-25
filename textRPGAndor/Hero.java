package textRPGAndor;

public class Hero extends GameCharacter { // Класс "герой" наследуется от класса "игровой персонаж"
    //private int posX;
    //private int posY;
    //public int lastPosX;
    //public int lastPosY;
    private int killedMonsterCounter;
    private int currentZone;
    public int getX() { return posX; }
    public int getY() { return posY; }
    public Inventory myInv = new Inventory();
    
    //Класс, Имя, Пол, Ранг, Сила, Воля, Инвентарь
    public Hero(EnumClass _charClass, String _name, EnumGender _gender, int _rank, int _strength, int _will) {
        super(_charClass, _name, _gender, _rank, _strength, _will);
        currentZone = 0;
        killedMonsterCounter = 0;
        myInv.add(new Item("Слабый камень здоровья", Item.ItemType.InfConsumables));
        myInv.add(new Item("Слабое зелье лечения", Item.ItemType.Consumables));
        myInv.addSomeCoins(2);
    }

    public int getZoneDangerous()
    {
        return currentZone;
    }    
    
    public void goToDangerousZone()
    {
        currentZone++;
        System.out.println("Герой переходит в зону опасности " + currentZone);
    }
    
    public void setXY(int _x, int _y)
    {
        posX = _x;
        posY = _y;
    }
    
    public void moveHero(int vx, int vy)
    {
        lastPosX = posX;
        lastPosY = posY;
        posX += vx;
        posY += vy;
    }
    
    public void expGain() {
        System.out.println("Герой повышает свою силу на 1");
        strength++;
    }

    public int getKilledMonsterCounter(){
        return killedMonsterCounter;
    }

    public void addMonsterCounter(){
        killedMonsterCounter++;
    }

    public void rewardGain(EnumClass _charClass) {
        int inpInt = 0;
        switch (_charClass) {
            case Гор:
                inpInt = getAction(1, 3, "За победу над гором вы возьмёте: 1. Две ед. воли 2. Две монеты 3. Одну ед. воли и одну монету");
                if (inpInt == 1){will = addWill(will,2);}
                if (inpInt == 2){myInv.addSomeCoins(2);}
                if (inpInt == 3){will = addWill(will,1); myInv.addSomeCoins(1);}
                break;
            case Скраль:
                inpInt = getAction(1, 3, "За победу над скралем вы возьмёте: 1. Четыре ед. воли 2. Четыре монеты 3. Две ед. воли и две монеты");
                if (inpInt == 1){will = addWill(will,4);}
                if (inpInt == 2){ myInv.addSomeCoins(4);}
                if (inpInt == 3){will = addWill(will,2); myInv.addSomeCoins(2);}
                break;
            case Вардрак:
                inpInt = getAction(1, 3, "За победу над вардраком вы возьмёте: 1. Шесть ед. воли 2. Шесть монет 3. Три ед. воли и три монеты");
                if (inpInt == 1){will = addWill(will,6);}
                if (inpInt == 2){ myInv.addSomeCoins(6);}
                if (inpInt == 3){will = addWill(will,3); myInv.addSomeCoins(3);}
                break;
            case Тролль:
                inpInt = getAction(1, 3, "За победу над троллем вы возьмёте: 1. Шесть ед. воли 2. Шесть монет 3. Три ед. воли и три монеты");
                if (inpInt == 1){will = addWill(will,6);}
                if (inpInt == 2){ myInv.addSomeCoins(6);}
                if (inpInt == 3){will = addWill(will,3); myInv.addSomeCoins(3);}
                break;
            default:
                break;
        }
    }

    private int addWill(int will, int add){
        if (will+add > 20){
            return 20;
        } else return will + add;
    }

    public void defeated() {
        if (strength > 1) {
            strength = strength - 1;
        }
        will = 3;
    }
    
    public int getAction(int _min, int _max, String _str) {
        int x = 0;
        do {
            if (!"".equals(_str)) {
                System.out.println(_str);
            }
            x = Utils.sc.nextInt();
        } while (x < _min || x > _max);
        return x;
    }
}
