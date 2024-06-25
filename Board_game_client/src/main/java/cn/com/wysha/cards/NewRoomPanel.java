package cn.com.wysha.cards;

import cn.com.wysha.Main;
import cn.com.wysha.online.Room;

import javax.swing.*;

public class NewRoomPanel{
    public final String cardName=this.getClass().getName();
    public JPanel panel;
    private JButton startButton;
    private JSpinner width;
    private JSpinner height;
    private JTextField name;

    public NewRoomPanel() {
        startButton.addActionListener(e -> {
            Main.mainForm.setVisible(false);
            new Room(name.getText(), (Integer) width.getValue(), (Integer) height.getValue());
        });
    }
}
