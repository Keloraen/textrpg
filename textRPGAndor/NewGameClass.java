package textRPGAndor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewGameClass extends Canvas implements Runnable{
    public static int WIDTH = 1000;
    public static int HEIGHT = 300;
    public static String NAME = "BASICS";

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    //public static Sprite hero, gor, mountain, questhut, gameshop, skraal, tree, troll, wardrack;
    private static int heroPosX = 50;
    private static int heroPosY = 50;
    private boolean running;

    //перенесённые переменные
    private Hero[] heroPattern = new Hero[8];
    private Monster[] monsterPattern = new Monster[4];
    private Hero mainHero;
    private Monster currentMonster;
    private int currentRound;
    private int day = 1;
    private int hour = 1;
    private GameMap map;
    private InGameShop gameShop;
    private QuestHut questHut;
    private int inpInt = 0;
    //-----------------------
    mainGUI guiPanel;
    GameCharacter gameCharactersArray[] = new GameCharacter[100];

    public NewGameClass(){
        NewGameClass game = this;
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        //опции главного окна
        JFrame frame = new JFrame(game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //создадим панель с доп. гуем
        guiPanel = new mainGUI(game);

        //разместим на главном окне компоненты
        frame.add(game, BorderLayout.CENTER);
        frame.add(guiPanel.scrollPane, BorderLayout.SOUTH);
        frame.add(guiPanel.utilPanel,BorderLayout.EAST);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        this.start();
    }


    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void run() {
        init();
        //перенесённые методы
        map = new GameMap();
        gameShop = new InGameShop();
        questHut = new QuestHut();
        //-------------------
        while(running) {
            render();   //отрисовывает картинку
            update();   //пока ничего не делает
        }
    }

    public void init() {
        //создадим заготовки всех героев
        heroPattern[0] = new Hero(EnumClass.Воин, "Торн", EnumGender.Мужчина, 14, 1, 7, getSprite("man.png"));
        heroPattern[1] = new Hero(EnumClass.Лучник, "Паско", EnumGender.Мужчина, 25, 1, 7, getSprite("man.png"));
        heroPattern[2] = new Hero(EnumClass.Гном, "Крам", EnumGender.Мужчина, 7, 1, 7, getSprite("man.png"));
        heroPattern[3] = new Hero(EnumClass.Волшебник, "Лифардус", EnumGender.Мужчина, 34, 1, 7, getSprite("man.png"));
        heroPattern[4] = new Hero(EnumClass.Воин, "Майрен", EnumGender.Женщина, 14, 1, 7, getSprite("man.png"));
        heroPattern[5] = new Hero(EnumClass.Лучник, "Чада", EnumGender.Женщина, 25, 1, 7, getSprite("man.png"));
        heroPattern[6] = new Hero(EnumClass.Гном, "Байт", EnumGender.Женщина, 7, 1, 7, getSprite("man.png"));
        heroPattern[7] = new Hero(EnumClass.Волшебник, "Эара", EnumGender.Женщина, 34, 1, 7, getSprite("man.png"));

        //создадим заготовки всех монстров
        monsterPattern[0] = new Monster(EnumClass.Гор, "Гор", EnumGender.Мужчина, 0, 2, 4, getSprite("gor.png"));
        monsterPattern[1] = new Monster(EnumClass.Скраль, "Скраль", EnumGender.Мужчина, 0, 6, 6, getSprite("skraal.png"));
        monsterPattern[2] = new Monster(EnumClass.Вардрак, "Вардрак", EnumGender.Мужчина, 0, 12, 7, getSprite("wardrack.png"));
        monsterPattern[3] = new Monster(EnumClass.Тролль, "Тролль", EnumGender.Мужчина, 0, 14, 12, getSprite("troll.png"));

        currentRound = 1;

        addKeyListener(new KeyInputHandler());

        //создадим главного героя
        mainHero = heroPattern[Utils.rand.nextInt(8)];
        //guiPanel.setText(mainHero.showInfoString());
        guiPanel.setGameText(guiPanel.getGameText() + "\n" + mainHero.showInfoString());
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.getHSBColor(200,222,200)); //выбрать цвет
        g.fillRect(0, 0, getWidth(), getHeight()); //заполнить прямоугольник
        //разместим героя на карте
        mainHero.sprite.draw(g, heroPosX, heroPosY);
        questHut.sprite.draw(g, 0, 0);
        gameShop.sprite.draw(g, 0, 100);
        //метод выше заменить на общий метод Отрисовать все объекты по массиву map
        g.dispose();
        bs.show(); //показать
    }

    public void update() {
    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return sprite;
    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (heroPosX-50 >= 0 && heroPosX-50<WIDTH) heroPosX = heroPosX - 50;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (heroPosX+50 >= 0 && heroPosX+50<WIDTH) heroPosX = heroPosX + 50;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (heroPosY-50 >= 0 && heroPosY-50<HEIGHT) heroPosY = heroPosY - 50;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (heroPosY+50 >= 0 && heroPosY+50<HEIGHT) heroPosY = heroPosY + 50;
            }
        }
    }


    public void battle(mainGUI gui) {
        currentRound = 1;
        int heroDamage = 0;
        int monsterDamage = 0;
        currentMonster = monsterPattern[Utils.rand.nextInt(4)];
        //mainHero = this.mainHero;

        //флаг окончания боя
        boolean flag = true;
        // Выводим сообщение о выходе нового врага на поле боя
        //System.out.println("На арену выходит " + currentMonster.getName());
        gui.setGameText(gui.getGameText() + "\nНа арену выходит " + currentMonster.getName());
        do {
            // зануляем переменные
            heroDamage = 0;
            monsterDamage = 0;
            // Выводим информацию о текущем раунде, состоянии героя и врага
            gui.setGameText(gui.getGameText() +"\nТекущий раунд: " + currentRound);
            gui.setGameText(gui.getGameText() + "\n" + mainHero.showInfoString());
            gui.setGameText(gui.getGameText() + "\n" + currentMonster.showInfoString());
            //тут надо добавить кнопки на гуй и ждать от него какой-то реакции
            int response = gui.getResponse();

            //временна затычка от бесконечного цикла
            flag = false;
        }while (flag);

        do {

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
                    //int invInput = getAction(0, mainHero.myInv.getSize(), "Выберите предмет для использования");
//                    String usedItem = mainHero.myInv.useItem(invInput);
//                    if (!"".equals(usedItem)) {
//                        System.out.print(mainHero.getName() + " использует " + usedItem + " и");
//                        mainHero.useItem(usedItem);
//                    } else {
//                        System.out.println(mainHero.getName() + " просто закрывает сумку");
//                        //System.out.println("======================================================================");
//                    }
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

    public void time(){
        if(hour != 7){
            hour++;
        } else{
            day++;
            hour = 1;
        }
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
