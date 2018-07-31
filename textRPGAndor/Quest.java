package textRPGAndor;

//import com.sun.org.glassfish.gmbal.Description;

class Quest {
    private final String description;
    private final int monster;
    private final int count;
    private final int reward;
    
    public Quest(String description, int monster, int count, int reward){
        this.description = description;
        this.monster = monster;
        this.count = count;
        this.reward = reward;
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
