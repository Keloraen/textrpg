package textRPGAndor;

public class GameCharacter extends GameObject {

    private Inventory myInv;
    private String name;

    //конструктор старой версии
    public GameCharacter(EnumClass charClass, String name, EnumGender gender, int rank, int strength, int will) {
        this.charClass = charClass;
        this.name = name;
        this.gender = gender;
        this.rank = rank;
        this.strength = strength;
        this.will = will;
    }

    public String getName() {
        return name;
    }

    private EnumClass charClass;

    public EnumClass getCharClass() {
        return charClass;
    }

    private EnumGender gender;

    public EnumGender getGender() {
        return gender;
    }
    private int rank;

    public int getRank() {
        return rank;
    }
    public int strength;

    public int getStrength() {
        return strength;
    }
    public int will;

    public int getWill() {
        return will;
    }

    public boolean isAlive() {
        return will >= 1;
    }

    //конструктор новой версии
    public GameCharacter(EnumClass charClass, String name, EnumGender gender, int rank, int strength, int will, Sprite sprite) {
        //Класс, Имя, Пол, Ранг, Сила, Воля, Инвентарь
        this.charClass = charClass;
        this.name = name;
        this.gender = gender;
        this.rank = rank;
        this.strength = strength;
        this.will = will;
        this.sprite = sprite;
    }

    public void showInfo() // Вывод инфо по персонажу
    {
        System.out.println(name + " Сила: " + strength + " Воля: " + will);
    }

    public String showInfoString(){
        return name + " Сила: " + strength + " Воля: " + will;
    }

    @Override
    public Object clone() throws CloneNotSupportedException // Копирование объектов 
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Клонирование невозможно");
            return this;
        }
    }

    public int makeAttack(int will, EnumClass charClass, int strength) {
        //занулим результаты бросков кубиков и другие перменные
        int currentAttack = 0; //String output = new String(""); 
        switch (charClass) {
            case Воин:
                //0-6 2, 7-13 3, 14-20 4
                if (will <= 6) {
                    currentAttack = strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 7) && (will <= 13)) {
                    currentAttack = strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 14) && (will <= 20)) {
                    currentAttack = strength + throwDices(4);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                }
                break;
            case Лучник:
                //0-6 3, 7-13 4, 14-20 5
                if (will <= 6) {
                    currentAttack = strength + throwArcherDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 7) && (will <= 13)) {
                    currentAttack = strength + throwArcherDices(4);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 14) && (will <= 20)) {
                    currentAttack = strength + throwArcherDices(5);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                }
                break;
            case Гном:
                //0-6 1, 7-13 2, 14-20 3
                if (will <= 6) {
                    currentAttack = strength + throwDices(1);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 7) && (will <= 13)) {
                    currentAttack = strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if ((will >= 14) && (will <= 20)) {
                    currentAttack = strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                }
                break;
            case Волшебник:
                //для него всегда 1 кубик, но можно переворачивать
                int mageres = throwDices(1);
                System.out.println(name + " выбрасывает " + mageres + ", итого " + (mageres + strength));
                int x = getAction(0, 1, "Перевернуть? 1-Да, 0-Нет");
                if (x == 1) {
                    currentAttack = 7 - mageres + strength;
                    System.out.println(name + " переворачивает кубик, получая " + (currentAttack - strength) + ", итого " + currentAttack);
                } else {
                    currentAttack = mageres + strength;
                }
                break;
            //-------------------------АТАКИ МОНСТРОВ----------------------------------
            case Гор:
                currentAttack = strength + throwDices(2);
                System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                break;
            case Скраль:
                currentAttack = strength + throwDices(2);
                System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                break;
            case Вардрак:
                if (will <= 6) {
                    currentAttack = strength + throwBlackDices(1);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else if (will >= 7) {
                    currentAttack = strength + throwBlackDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                }
                break;
            case Тролль:
                if (will <= 6) {
                    currentAttack = strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                } else {
                    currentAttack = strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - strength) + ", итого " + currentAttack);
                }
                break;
            default:
                break;
        }
        return currentAttack;
    }

    public void getDamage(int _inputDamage) // Метод получения урона, на вход подаётся урон - целое число
    {

        if (_inputDamage < 0) {
            _inputDamage = 0; // делаем прверку на отрицательный урон, для предотвращения эффекта лечения
        }
        System.out.println(name + " получает " + _inputDamage + " ед. урона");
        will -= _inputDamage; // снижаем уровень здоровья

    }

    private int throwDices(int i) {
        int arr[] = new int[i];
        int maxres = 0;
        System.out.print("Выброшено:");
        //массив с результатами бросков
        for (int j = 0; j < arr.length; j++) {
            arr[j] = Utils.rand.nextInt(6) + 1;
            System.out.print(" " + arr[j]);
            if (arr[j] >= maxres) {
                maxres = arr[j];
            }
        }
        //проверим пары
        if (i>1) {
            int doublet = 0;
            if (arr.length >= 2) {
                for (int j = 0; j < arr.length; j++) {
                    int tekres = arr[j];
                    for (int k = j + 1; k < arr.length; k++) {
                        if (tekres == arr[k]) {
                            System.out.print(" (есть пара!)");
                            doublet = tekres * 2;
                        }
                    }
                    tekres = arr[j];
                }
            }
            maxres = Math.max(doublet, maxres);
            System.out.print(", max - " + maxres + ". ");
        } else System.out.print(" ");
        return maxres;
    }

    private int throwBlackDices(int i) {
        int maxres = 0;
        int dice = 0;
        System.out.print("Выброшено:");
        for (int j = 0; j < i; j++) {
            int[] arr = {6, 6, 8, 10, 10, 12};
            dice = Utils.rand.nextInt(6);
            System.out.print(" " + arr[dice]);
            if (arr[dice] >= maxres) {
                maxres = arr[dice];
            }
        }

//        //проверим пары
//        int doublet = 0;
//        if (arr.length >= 2) {
//            for (int j = 0; j < arr.length; j++) {
//                int tekres = arr[j];
//                for (int k = j + 1; k < arr.length; k++) {
//                    if (tekres == arr[k]) {
//                        System.out.println("есть пара!");
//                        doublet = tekres * 2;
//                    }
//                }
//                tekres = arr[j];
//            }
//        }
//        maxres = Math.max(doublet, maxres);
        System.out.print(", max - " + maxres + ". ");
        return maxres;
    }

    private int throwArcherDices(int i) {
        int dice = 0;
        for (int j = 1; j <= i; j++) {
            dice = Utils.rand.nextInt(6) + 1;
            if (j != i) {
                System.out.print(name + " делает " + j + "/" + i + " бросок. Результат - " + dice);
                int inp = getAction(0, 2, " Перебросить? 1-Да, 0-Нет");
                if (inp == 1) {
                    continue;
                } else {
                    break;
                }
            } else {
                System.out.print(name + " делает " + j + "/" + i + " бросок. Результат - " + dice + "\n");
            }
        }
        return dice;
    }

    public void cure(int _val) {
        will += _val;
        if (will > 20) {
            will = 20;
        }
    }

    public void useItem(String _item) {
        switch (_item) {
            case "Слабое зелье лечения":
                cure(10);
                System.out.println(" пополняет волю на 10 ед.");
                break;
            case "Слабый камень здоровья":
                cure(3);
                System.out.println(" пополняет волю на 3 ед.");
                break;
            default:
                System.out.println(" ничего не происходит");
                break;
        }
    }

    public int getAction(int _min, int _max, String _str) {
        int x = 0;
        do {
            if (!"".equals(_str)) {
                System.out.println(_str);
            }
            //x = Utils.sc.nextInt();
        } while (x < _min || x > _max);
        return x;
    }
}
