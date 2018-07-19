package textRPGAndor;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class GameClass {

    private Hero[] heroPattern = new Hero[8];
    private Monster[] monsterPattern = new Monster[4];
    private Hero mainHero;
    private Monster currentMonster;

    private int currentRound;
    private int day = 1;
    private int hour = 1;
    private GameMap map;
    private InGameShop shop;
    private QuestHut hut;
    private int inpInt = 0;

    public GameClass() {
        initGame();
        map = new GameMap();
        shop = new InGameShop();
        hut = new QuestHut();
        System.out.println("Игра началась!");
        selectHero();
        mainHero.setXY(1, 1);
        map.updateMap(mainHero.getX(), mainHero.getY());
        map.showMap(mainHero, getTime());     //рисуем карту
    }

    public void mainGameLoop()  // Метод, отвечающий за игровую логику
    {
        boolean flag = true;
        String actionsList;
        //System.out.println("Начинается день " + day + ". Игровой час " + hour);
        actionsList = makeActionList();
        while (flag) {
/*            int x = getAction(0, 7, "Что Вы хотите делать дальше 0.Завершить игру 1.Пойти влево 2."
                    + "Пойти вправо\n3.Пойти вверх 4.Пойти вниз 5.Сразиться на арене 6.Открыть инвентарь"
                    + " 7.Показать инфо");*/
            actionsList = makeActionList();
            int x = getAction(0,7,actionsList);

            switch (x) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    if (map.isCellEmpty(mainHero.getX() - 1, mainHero.getY(),true)) {
                        mainHero.moveHero(-1, 0);
                        time();
                        randomEvents();
                    }
                    break;
                case 2:
                    if (map.isCellEmpty(mainHero.getX() + 1, mainHero.getY(),true)) {
                        mainHero.moveHero(1, 0);
                        time();
                        randomEvents();
                    }
                    break;
                case 3:
                    if (map.isCellEmpty(mainHero.getX(), mainHero.getY() - 1,true)) {
                        mainHero.moveHero(0, -1);
                        time();
                        randomEvents();
                    }
                    break;
                case 4:
                    if (map.isCellEmpty(mainHero.getX(), mainHero.getY() + 1,true)) {
                        mainHero.moveHero(0, 1);
                        time();
                        randomEvents();
                    }
                    break;
                case 5: {
                    try {
                        currentMonster = (Monster) monsterPattern[1].clone();  // Создаем монстра путем копирования из шаблона                               
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(GameClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                time();
                battle(mainHero, currentMonster);
                break;
                case 6:
                    mainHero.myInv.showAllItems(true);
                    int invInput = getAction(0, mainHero.myInv.getSize(), "Выберите предмет для использования");
                    String usedItem = mainHero.myInv.useItem(invInput);
                    if (!"".equals(usedItem)) {
                        System.out.print(mainHero.getName() + " использует " + usedItem + " и");
                        mainHero.useItem(usedItem);
                    } else {
                        System.out.println(mainHero.getName() + " просто закрывает сумку");
                        System.out.println("======================================================================");
                    }
                    break;
                case 7:
                    mainHero.ShowInfo();
                    System.out.println("Убито монстров: " + mainHero.getKilledMonsterCounter());
                    mainHero.myInv.showAllItems(false);
                    break;
                default:
                    break;
            }
            if (!flag) {
                break;
            }

            //вход в лавку торговца
            if (map.getObstValue(mainHero.getX(), mainHero.getY()) == 'S') {
                boolean flag1 = true;
                while (flag1) {
                    flag1 = shopAction();
                }
                //mainHero.moveHero(-1, 0);
                mainHero.setXY(mainHero.lastPosX, mainHero.lastPosY);
                System.out.println(mainHero.getName() + " выходит из лавки");
            }
            //вход в хижину провидца
            if (map.getObstValue(mainHero.getX(), mainHero.getY()) == 'Q') {
                boolean flag1 = true;
                while (flag1) {
                    flag1 = hutAction();
                }
                //mainHero.moveHero(1, 0);
                mainHero.setXY(mainHero.lastPosX, mainHero.lastPosY);
                System.out.println(mainHero.getName() + " выходит из хижины");
            }

            map.updateMap(mainHero.getX(), mainHero.getY());
            map.showMap(mainHero,getTime());
            System.out.println("======================================================================");
            if (!mainHero.isAlive()) {
                break;
            }
        }

        System.out.println("Игра завершена");
    }

    private String makeActionList() {
        String actionsList = "";
        actionsList = "Что Вы хотите делать дальше 0.Завершить игру ";
        if (map.isCellEmpty(mainHero.getX() - 1, mainHero.getY(), false)){
            actionsList = actionsList + "1.Пойти влево ";
        }
        if (map.isCellEmpty(mainHero.getX() + 1, mainHero.getY(), false)){
            actionsList = actionsList + "2.Пойти вправо ";
        }
        if (map.isCellEmpty(mainHero.getX(), mainHero.getY() - 1, false)){
            actionsList = actionsList + "3.Пойти вверх ";
        }
        if (map.isCellEmpty(mainHero.getX(), mainHero.getY() + 1, false)){
            actionsList = actionsList + "4.Пойти вниз ";
        }
        actionsList += "5.Сразиться на арене 6.Открыть инвентарь 7.Показать инфо";
        return actionsList;
    }

    public void randomEvents() {
        //случайное событие - нападение монстра
        if (Utils.rand.nextInt(100) < 15) {
            System.out.print("На Вас внезапно нападает ");
            try {
                currentMonster = (Monster) monsterPattern[1].clone();  // Создаем монстра путем копирования из шаблона
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GameClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(currentMonster.getName());
            battle(mainHero, currentMonster);
        }

        //случайное событие - найден артефакт
        if (Utils.rand.nextInt(100) < 10) {
            mainHero.myInv.add(new Item("Слабое зелье лечения", Item.ItemType.Consumables));
            System.out.println("Герой находит оброненное кем-то Слабое зелье лечения");
        }
    }
    
    public void time(){
        if(hour != 7){
            hour++;
        } else{
            day++;
            hour = 1;
        }
    }

    public void battle(Hero h, Monster m) {
        currentRound = 1;
        int heroDamage = 0;
        int monsterDamage = 0;
        currentMonster = m;
        mainHero = h;
        boolean flag = true;
        System.out.println("На арену выходит " + currentMonster.getName()); // Выводим сообщение о выходе нового врага на поле боя
        do {
            // зануляем переменные
            heroDamage = 0;
            monsterDamage = 0;
            // Выводим информацию о текущем раунде, состоянии героя и врага
            System.out.println("\nТекущий раунд: " + currentRound);
            mainHero.ShowInfo();
            currentMonster.ShowInfo();

            inpInt = getAction(0, 3, "Ход игрока: 0.Завершить бой 1.Атака 2.Открыть инвентарь 3.Сменить монстра");
            System.out.print("\n"); // Печатаем два символа перевода строки
            switch (inpInt) { // Герой атакует
                case 1: {
                    //бросок кубиков героя
                    heroDamage = mainHero.makeAttack(mainHero.getWill(), mainHero.getCharClass(), mainHero.getStrength());
                    //бросок кубиков монстра
                    monsterDamage = currentMonster.makeAttack(currentMonster.getWill(), currentMonster.getCharClass(), currentMonster.getStrength());
                    //расчёт результата
                    if (heroDamage > monsterDamage) {
                        //урон монстру
                        currentMonster.getDamage(heroDamage - monsterDamage);
                    } else if (monsterDamage > heroDamage) {
                        //урон герою
                        mainHero.getDamage(monsterDamage - heroDamage);
                    } else if (heroDamage == monsterDamage) {
                        //ничья, ничего не делаем, а на самом деле снимаем час времени
                        System.out.println("Ничья. Проходит час игрового времени");
                    }
                    time();
                    getTime();
                    //проверка жив ли монстр или герой
                    if (!mainHero.isAlive()) // Если после удара монстра герой погибает - выходим из основного игрового цикла
                    {
                        System.out.println("Герой " + mainHero.getName() + " проигрывает бой. Сила уменьшена на 1, воля = 3.");
                        mainHero.defeated();
                        flag = false;
                    }

                    if (!currentMonster.isAlive()) // Делаем проверку жив ли монстр после удара героя
                    {
                        System.out.println(currentMonster.getName() + " погибает"); // Печатаем сообщение о гибели монстра
                        mainHero.expGain();
                        mainHero.rewardGain(currentMonster.getCharClass());
                        mainHero.addMonsterCounter();
                        System.out.println("");
                        try {
                            currentMonster = (Monster) monsterPattern[Utils.rand.nextInt(4)].clone(); // Создаем нового монстра случайного типа, копируя из шаблона
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(GameClass.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("На арену выходит " + currentMonster.getName()); // Выводим сообщение о выходе нового врага на поле боя
                    }
                    currentRound++;
                    break;
                }

                case 2: {
                    mainHero.myInv.showAllItems(true);
                    int invInput = getAction(0, mainHero.myInv.getSize(), "Выберите предмет для использования");
                    String usedItem = mainHero.myInv.useItem(invInput);
                    if (!"".equals(usedItem)) {
                        System.out.print(mainHero.getName() + " использует " + usedItem + " и");
                        mainHero.useItem(usedItem);
                    } else {
                        System.out.println(mainHero.getName() + " просто закрывает сумку");
                        //System.out.println("======================================================================");
                    }
                    break;
                }

                case 3: {
                    System.out.print(mainHero.getName() + " не хочет сражаться с этим монстром и ");
                    try {
                        currentMonster = (Monster) monsterPattern[Utils.rand.nextInt(4)].clone(); // Создаем нового монстра случайного типа, копируя из шаблона
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(GameClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("на арену выходит " + currentMonster.getName()); // Выводим сообщение о выходе нового врага на поле боя
                    currentRound = 1;
                    break;
                }
                case 0: {
                    // В зависимости от того, кто остался в живых - выводим итоговое сообщение о результате игры
                    if (currentMonster.isAlive() && mainHero.isAlive()) {
                        System.out.println(mainHero.getName() + " трусливо сбегает с арены");
                        flag = false;
                        break; // Выход из боя
                    }
                }
            }
            System.out.println("======================================================================");
        } while (flag);
    }

    public void selectHero() {
        for (int i = 0; i < 8; i++) {
            System.out.println((i + 1) + ". " + heroPattern[i].getName() + ", класс " + heroPattern[i].getCharClass() + ", " + heroPattern[i].getGender());
        }
        inpInt = getAction(1, 8, "Выберите героя:");
        try {
            mainHero = (Hero) heroPattern[inpInt - 1].clone(); // Создаем героя путем копирования из шаблона
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GameClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(mainHero.getName() + " начинает свое путешествие");
    }

    public boolean shopAction() {
        shop.showItems(mainHero);
        int x = getAction(0, shop.ITEMS_COUNT, "Введите номер покупаемого товара. Для выхода введите 0");
        if (x == 0) {
            return false;
        } else {
            shop.buyByHero(x - 1, mainHero);
            return true;
        }
    }

    public boolean hutAction() {
        hut.showAvailableQuests();
        int x = getAction(0, hut.QUEST_COUNT, "За какое задание вы возьмётесь? Для выхода введите 0");
        if (x == 0) {
            return false;
        } else {
            //shop.buyByHero(x - 1, mainHero);
            hut.takeQuest();
            return true;
        }
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

    public void initGame() // Инициализируем начальное состояние игры
    {
        // Задаем шаблоны героев и монстров
        //Класс, Имя, Пол, Ранг, Сила, Воля,
        heroPattern[0] = new Hero(EnumClass.Воин, "Торн", EnumGender.Мужчина, 14, 1, 7);
        heroPattern[1] = new Hero(EnumClass.Лучник, "Паско", EnumGender.Мужчина, 25, 1, 7);
        heroPattern[2] = new Hero(EnumClass.Гном, "Крам", EnumGender.Мужчина, 7, 1, 7);
        heroPattern[3] = new Hero(EnumClass.Волшебник, "Лифардус", EnumGender.Мужчина, 34, 1, 7);
        heroPattern[4] = new Hero(EnumClass.Воин, "Майрен", EnumGender.Женщина, 14, 1, 7);
        heroPattern[5] = new Hero(EnumClass.Лучник, "Чада", EnumGender.Женщина, 25, 1, 7);
        heroPattern[6] = new Hero(EnumClass.Гном, "Байт", EnumGender.Женщина, 7, 1, 7);
        heroPattern[7] = new Hero(EnumClass.Волшебник, "Эара", EnumGender.Женщина, 34, 1, 7);

        monsterPattern[0] = new Monster(EnumClass.Гор, "Гор", EnumGender.Мужчина, 0, 2, 4);
        monsterPattern[1] = new Monster(EnumClass.Скраль, "Скраль", EnumGender.Мужчина, 0, 6, 6);
        monsterPattern[2] = new Monster(EnumClass.Вардрак, "Вардрак", EnumGender.Мужчина, 0, 12, 7);
        monsterPattern[3] = new Monster(EnumClass.Тролль, "Тролль", EnumGender.Мужчина, 0, 14, 12);

        currentRound = 1;
    }

    private String getTime(){
        String time;
        if(hour != 7){
            time = "Идёт день " + day + ". Игровой час " + hour;
        } else{
            time = "Начинается день " + day;
        }
        return time;
    }
}
