package textRPGAndor;

//import com.sun.org.glassfish.gmbal.Description;

class Quest {
    public final String description;
    public final int monster;
    public final int count;
    public final int reward;
    
    public Quest(String _description, int _monster, int _count, int _reward){
        description = _description;
        monster = _monster;
        count = _count;
        reward = _reward;
    }

    public String getDescription() {
        return description;
    }

    public int getMonster() {
        return monster;
    }

    public int getCount() {
        return count;
    }

    public int getReward() {
        return reward;
    }
}
