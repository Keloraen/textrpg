package textRPGAndor;

public class GameMap {
    
    private final int msx = 20;     //размер карты в ширину
    private final int msy = 6;      //размер карты в длину
    private final String[][] map = new String[msy][msx];      //массив хранит отображаемые символы *,H, S, X и другие
    private final char[][] obstMap = new char[msy][msx];      //карта препятствий, зданий и прочего
    
    public GameMap()
    {
        obstMap[0][0] = 'Q';
        obstMap[2][0] = 'S';

        //сгенерировать карту размером msx на msy за исключением левого верхнего угла 3х3
        for (int i = 0; i < msx; i++) {
            for (int j = 0; j < msy; j++) {
                if ( i<3 && j<3) continue;
                float rnd = (float) Math.random();
                if (rnd >=0.7){
                    obstMap[j][i] = 'T';
                }
                if (rnd >=0.85){
                    obstMap[j][i] = 'M';
                }
            }
        }

        //населить карту монстрами

    }
    
    public char getObstValue(int x, int y)
    {
        return obstMap[y][x];
    }
    
    public boolean isCellEmpty(int x, int y, boolean printWarnings)
    {
        if(x < 0 || y < 0 || x > msx - 1 || y > msy - 1) return false;
        if (obstMap[y][x] == 'T' || obstMap[y][x] == 'M' || obstMap[y][x] == 'X'){
            if (printWarnings) System.out.println("Перед героем преграда, туда пойти нельзя");
            return false;
        }
        return true;
    }
    
    public void updateMap(int hx, int hy) {
        for (int i = 0; i < msy; i++) {
            for (int j = 0; j < msx; j++) {
                map[i][j] = ".";
                if(obstMap[i][j] == 'S') map[i][j] = "S";   //магазин
                if(obstMap[i][j] == 'T') map[i][j] = "T";   //дерево
                if(obstMap[i][j] == 'M') map[i][j] = "M";   //гора
                if(obstMap[i][j] == 'Q') map[i][j] = "Q";   //хижина
                if(obstMap[i][j] == 'X') map[i][j] = "X";   //монстр
            }
        }
        map[hy][hx] = "H";    //герой
    }

    public void showMap(Hero mainHero, String time) {
        //System.out.println("H - ваш герой, S - лавка торговца, Q - хижина провидца, X - препятствие");
        String will = "";
        for (int i = 0; i < mainHero.getWill(); i++) {
            will += "{} ";
        }
        String strength = "";
        for (int i = 0; i < mainHero.getStrength(); i++) {
            strength += "[]";
        }
        int gold = mainHero.myInv.getGold();

        for (int i = 0; i < msy; i++) {
            for (int j = 0; j < msx; j++) {
                System.out.print(map[i][j] + " ");
            }
            //выведем легенду и статы
            switch (i){
                case 0: {
                    System.out.println("    " + time);
                    break;
                }
                case 1: {
                    System.out.println("    H - ваш герой, S - лавка торговца, Q - хижина провидца");
                    break;
                }
                case 2: {
                    System.out.println("    T - деревья, M - горы, X - монстры");
                    break;
                }
                case 3: {
                    System.out.println("    " + mainHero.getCharClass() + " " + mainHero.getName() + ": сила " + strength + ", воля " + will + ", золото " + gold);
                    break;
                }
                case 4: {
                    System.out.println("    ");
                    break;
                }
                case 5: {
                    System.out.println("    ");
                    break;
                }
            }

        }
    }
}
