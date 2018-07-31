package textRPGAndor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JPanel{
    public JTextArea gameText = new JTextArea(5,100);
    public JScrollPane scrollPane = new JScrollPane(gameText);
    public JPanel utilPanel = new JPanel();
    public JButton buttonInventory = new JButton("Инвентарь");
    public JButton buttonFight = new JButton("Бой на арене");
    private NewGameClass game;
    private int response;

    public mainGUI(NewGameClass game){
        //родительский конструктор
        super();
        this.game = game;
        //и свои допилы
        ActionListener actionListenerFight = new FightActionListener();
        buttonFight.addActionListener(actionListenerFight);
        //нарисуем поле для игрового текста
        gameText.setText("Здесь будет различный игровой текст");
        //сделаем  панель для разных утилитарных кнопок
        utilPanel.setBorder(BorderFactory.createTitledBorder("Управление"));

        //добавим на панель нужные нам кнопки
        //utilPanel.setLayout(new FlowLayout());
        utilPanel.add(buttonInventory);
        utilPanel.add(buttonFight);

    }

    public void setGameText(String text){
        gameText.setText(text);
    }

    public String getGameText(){
        return gameText.getText();
    }

    public int getResponse() {

        return response;
    }


    private class FightActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            gameText.setText(gameText.getText() + "\n Начался бой");
            game.battle(mainGUI.this);
        }
    }
}
