package textRPGAndor;

public class GameCharacter extends GameObject {

    protected Inventory myInv;
    protected String name;

    public String getName() {
        return name;
    }

    protected EnumClass charClass;

    public EnumClass getCharClass() {
        return charClass;
    }

    protected EnumGender gender;

    public EnumGender getGender() {
        return gender;
    }
    protected int rank;

    public int getRank() {
        return rank;
    }
    protected int strength;

    public int getStrength() {
        return strength;
    }
    protected int will;

    public int getWill() {
        return will;
    }

    public boolean isAlive() {
        return will >= 1;
    }

    public GameCharacter(EnumClass _charClass, String _name, EnumGender _gender, int _rank, int _strength, int _will) {
        //Класс, Имя, Пол, Ранг, Сила, Воля, Инвентарь
        charClass = _charClass;
        name = _name;
        gender = _gender;
        rank = _rank;
        strength = _strength;
        will = _will;
    }

    public void ShowInfo() // Вывод инфо по персонажу
    {
        System.out.println(name + " Сила: " + strength + " Воля: " + will);
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

    public int makeAttack(int _will, EnumClass _charClass, int _strength) {
        //занулим результаты бросков кубиков и другие перменные
        int currentAttack = 0; //String output = new String(""); 
        switch (_charClass) {
            case Воин:
                //0-6 2, 7-13 3, 14-20 4
                if (_will <= 6) {
                    currentAttack = _strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 7) && (_will <= 13)) {
                    currentAttack = _strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 14) && (_will <= 20)) {
                    currentAttack = _strength + throwDices(4);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                }
                break;
            case Лучник:
                //0-6 3, 7-13 4, 14-20 5
                if (_will <= 6) {
                    currentAttack = _strength + throwArcherDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 7) && (_will <= 13)) {
                    currentAttack = _strength + throwArcherDices(4);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 14) && (_will <= 20)) {
                    currentAttack = _strength + throwArcherDices(5);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                }
                break;
            case Гном:
                //0-6 1, 7-13 2, 14-20 3
                if (_will <= 6) {
                    currentAttack = _strength + throwDices(1);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 7) && (_will <= 13)) {
                    currentAttack = _strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if ((_will >= 14) && (_will <= 20)) {
                    currentAttack = _strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                }
                break;
            case Волшебник:
                //для него всегда 1 кубик, но можно переворачивать
                int mageres = throwDices(1);
                System.out.println(name + " выбрасывает " + mageres + ", итого " + (mageres + _strength));
                int x = getAction(0, 1, "Перевернуть? 1-Да, 0-Нет");
                if (x == 1) {
                    currentAttack = 7 - mageres + _strength;
                    System.out.println(name + " переворачивает кубик, получая " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else {
                    currentAttack = mageres + _strength;
                }
                break;
            //-------------------------АТАКИ МОНСТРОВ----------------------------------
            case Гор:
                currentAttack = _strength + throwDices(2);
                System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                break;
            case Скраль:
                currentAttack = _strength + throwDices(2);
                System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                break;
            case Вардрак:
                if (will <= 6) {
                    currentAttack = _strength + throwBlackDices(1);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else if (will >= 7) {
                    currentAttack = _strength + throwBlackDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                }
                break;
            case Тролль:
                if (will <= 6) {
                    currentAttack = _strength + throwDices(2);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
                } else {
                    currentAttack = _strength + throwDices(3);
                    System.out.println(name + " выбрасывает " + (currentAttack - _strength) + ", итого " + currentAttack);
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
            x = Utils.sc.nextInt();
        } while (x < _min || x > _max);
        return x;
    }
}
