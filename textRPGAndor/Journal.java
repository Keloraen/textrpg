package textRPGAndor;

import java.util.ArrayList;

public class Journal {

    ArrayList<Quest> journ;

    public Journal() {
        journ = new ArrayList<>();
    }

    public void addQuest(Quest _quest) {
        journ.add(_quest);
    }

    public int getSize() {
        return journ.size();
    }

    public void readJournal() {
        System.out.println("Взятые квесты:");
        if (journ.size() > 0) {
            for (int i = 0; i < journ.size(); i++) {
                System.out.println(i + ". " + journ.get(i).getDescription());
            }
        } else {
            System.out.println("Журнал заданий пуст.");
        }

    }

}
