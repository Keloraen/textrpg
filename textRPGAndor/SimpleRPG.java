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

public class SimpleRPG extends Canvas implements Runnable{
    public static int WIDTH = 1000;
    public static int HEIGHT = 300;
    public static String NAME = "BASICS";

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public static Sprite hero, gor, mountain, questhut, gameshop, skraal, tree, troll, wardrack;
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
    private InGameShop shop;
    private QuestHut hut;
    private int inpInt = 0;
    //-----------------------

    GameCharacter gameCharactersArray[] = new GameCharacter[100];

    public static void main(String[] args) {
        SimpleRPG game = new SimpleRPG();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //нарисуем главное окно
        JFrame frame = new JFrame(SimpleRPG.NAME);
        //опции главного окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        //нарисуем лейбл для игрового текста
        JLabel gameText = new JLabel();
        gameText.setText("Здесь будет различный игровой текст");

        //сделаем  панель для разных утилитарных кнопок
        JPanel utilPanel = new JPanel();
        utilPanel.setBorder(BorderFactory.createTitledBorder("Управление"));

        //добавим на панель нужные нам кнопки
        JButton buttonInventory = new JButton("Инвентарь");
        utilPanel.add(buttonInventory);

        //разместим на главном окне компоненты
        frame.add(game, BorderLayout.CENTER);
        frame.add(gameText, BorderLayout.SOUTH);
        frame.add(utilPanel,BorderLayout.EAST);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();
        //new GameClass().mainGameLoop();
    }
    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void run() {
        init();
        //перенесённые методы
        map = new GameMap();
        shop = new InGameShop();
        hut = new QuestHut();
        //-------------------
        while(running) {
            render();   //отрисовывает картинку
            update();   //пока ничего не делает
        }
    }
    public void init() {
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

        addKeyListener(new KeyInputHandler());
        hero = getSprite("man.png");
        gor = getSprite("gor.png");
        mountain = getSprite("mountain.png");
        questhut = getSprite("questhut.png");
        gameshop = getSprite("shop.png");
        skraal = getSprite("skraal.png");
        tree = getSprite("tree.png");
        troll = getSprite("troll.png");
        wardrack = getSprite("wardrack.png");
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
        hero.draw(g, heroPosX, heroPosY);
        questhut.draw(g, 0, 0);
        gameshop.draw(g, 0, 100);
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

}
