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
            System.out.println("等待对手进入房间");
            Main.mainForm.setEnabled(false);
            new Room(name.getText(),Main.client.ID, (Integer) width.getValue(), (Integer) height.getValue());
        });
    }
}
