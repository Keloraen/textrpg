package textRPGAndor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class QuestHut extends MapObject{
    public final int QUEST_COUNT = 3;
    private final Quest[] quests = new Quest[QUEST_COUNT];
    public Sprite sprite = getSprite("questhut.png");
    
    public QuestHut(){
        quests[0] = new Quest("Убить 2 Горов за 5 монет", 1, 2, 5);
        quests[1] = new Quest("Убить 1 Вардрака за 15 монет", 1, 1, 15);
        quests[2] = new Quest("Убить 2 Скралей за 10 монет", 1, 2, 10);
    }
    
    public void showAvailableQuests(){
        System.out.println("Вы заходите в домик провидца. Он предлагает вам на выбор несколько заданий:");
        for (Quest quest : quests) {
            System.out.println(quest.getDescription());

        }
    }
    
    public void takeQuest(){
        
    }
}
