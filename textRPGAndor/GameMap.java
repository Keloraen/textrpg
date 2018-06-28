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
                    obstMap[j][i] = 'X';
                }
            }
        }

    }
    
    public char getObstValue(int x, int y)
    {
        return obstMap[y][x];
    }
    
    public boolean isCellEmpty(int _x, int _y)
    {
        if(_x < 0 || _y < 0 || _x > msx - 1 || _y > msy - 1) return false;
        return obstMap[_y][_x] != 'X';
    }
    
    public void updateMap(int _hx, int _hy) {
        for (int i = 0; i < msy; i++) {
            for (int j = 0; j < msx; j++) {
                map[i][j] = "*";
                if(obstMap[i][j] == 'S') map[i][j] = "S";
                if(obstMap[i][j] == 'X') map[i][j] = "X";
                if(obstMap[i][j] == 'Q') map[i][j] = "Q";
            }
        }
        map[_hy][_hx] = "H";
    }

    public void showMap(Hero mainHero, String time) {
        //System.out.println("H - ваш герой, S - лавка торговца, Q - хижина провидца, X - препятствие");
        String will = "";
        for (int i = 0; i < mainHero.getWill(); i++) {
            will += "[]";
        }
        String life = "";
        for (int i = 0; i < mainHero.getStrength(); i++) {
            life += "[]";
        }
        String gold = "";
        for (int i = 0; i < mainHero.myInv.getGold(); i++) {
            gold += "O ";
        }
        for (int i = 0; i < msy; i++) {
            for (int j = 0; j < msx; j++) {
                System.out.print(map[i][j] + " ");
            }
            //выведем легенду и статы
            switch (i){
                case 1: {
                    System.out.println("    H - ваш герой, S - лавка торговца, Q - хижина провидца");
                    break;
                }
                case 2: {
                    System.out.println("    T - деревья, M - горы, X - монстры");
                    break;
                }
                case 3: {
                    System.out.println("    " + mainHero.getCharClass() + " " + mainHero.getName() + ": сила " + life + ", воля " + will + ", золото " + gold);
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
                case 0: {
                    System.out.println("    " + time);
                    break;
                }
            }

        }
    }
}
